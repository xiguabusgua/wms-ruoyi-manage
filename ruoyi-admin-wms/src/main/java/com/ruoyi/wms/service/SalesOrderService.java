package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.exception.base.BaseException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.SalesOrderBo;
import com.ruoyi.wms.domain.bo.SalesOrderDetailBo;
import com.ruoyi.wms.domain.entity.Customer;
import com.ruoyi.wms.domain.entity.SalesOrder;
import com.ruoyi.wms.domain.entity.SalesOrderDetail;
import com.ruoyi.wms.domain.vo.SalesOrderDetailVo;
import com.ruoyi.wms.domain.vo.SalesOrderVo;
import com.ruoyi.wms.mapper.CustomerMapper;
import com.ruoyi.wms.mapper.SalesOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 销售订单Service业务层处理
 *
 * @author zcc
 */
@RequiredArgsConstructor
@Service
public class SalesOrderService {

    private final SalesOrderMapper salesOrderMapper;
    private final SalesOrderDetailService salesOrderDetailService;
    private final CustomerMapper customerMapper;

    /**
     * 查询销售订单详情（含明细列表、客户名称填充）
     */
    public SalesOrderVo queryById(Long id) {
        SalesOrderVo vo = salesOrderMapper.selectVoById(id);
        Assert.notNull(vo, "销售订单不存在");
        vo.setDetails(salesOrderDetailService.queryByOrderId(id));
        fillCustomerName(vo);
        return vo;
    }

    /**
     * 填充客户名称
     */
    private void fillCustomerName(SalesOrderVo vo) {
        if (vo.getCustomerId() != null) {
            Customer customer = customerMapper.selectById(vo.getCustomerId());
            if (customer != null) {
                vo.setCustomerName(customer.getCustomerName());
            }
        }
    }

    /**
     * 分页查询销售订单
     */
    public TableDataInfo<SalesOrderVo> queryPageList(SalesOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SalesOrder> lqw = buildQueryWrapper(bo);
        Page<SalesOrderVo> result = salesOrderMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询销售订单列表
     */
    public List<SalesOrderVo> queryList(SalesOrderBo bo) {
        LambdaQueryWrapper<SalesOrder> lqw = buildQueryWrapper(bo);
        return salesOrderMapper.selectVoList(lqw);
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<SalesOrder> buildQueryWrapper(SalesOrderBo bo) {
        LambdaQueryWrapper<SalesOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNo()), SalesOrder::getOrderNo, bo.getOrderNo());
        lqw.like(StringUtils.isNotBlank(bo.getOrderNo()) && bo.isLikeOrderNo(), SalesOrder::getOrderNo, bo.getOrderNo());
        lqw.eq(bo.getCustomerId() != null, SalesOrder::getCustomerId, bo.getCustomerId());
        lqw.eq(bo.getOrderStatus() != null, SalesOrder::getOrderStatus, bo.getOrderStatus());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增销售订单
     */
    @Transactional
    public Long insertByBo(SalesOrderBo bo) {
        validateOrderNo(bo.getOrderNo(), null);
        bo.setOrderStatus(0);
        calculateOrderSummary(bo);
        SalesOrder add = MapstructUtils.convert(bo, SalesOrder.class);
        salesOrderMapper.insert(add);
        bo.setId(add.getId());
        saveDetails(add.getId(), bo.getDetails());
        return add.getId();
    }

    /**
     * 修改销售订单
     */
    @Transactional
    public void updateByBo(SalesOrderBo bo) {
        SalesOrder update = MapstructUtils.convert(bo, SalesOrder.class);
        salesOrderMapper.updateById(update);
        List<SalesOrderDetail> detailList = MapstructUtils.convert(bo.getDetails(), SalesOrderDetail.class);
        handleDetailChanges(bo.getId(), detailList);
        detailList.forEach(it -> it.setOrderId(bo.getId()));
        salesOrderDetailService.saveDetails(detailList);
    }

    /**
     * 处理明细变更（删除不在新列表中的旧明细）
     */
    private void handleDetailChanges(Long orderId, List<SalesOrderDetail> detailList) {
        List<SalesOrderDetailVo> dbList = salesOrderDetailService.queryByOrderId(orderId);
        Set<Long> ids = detailList.stream()
            .filter(it -> it.getId() != null)
            .map(it -> it.getId())
            .collect(Collectors.toSet());
        List<SalesOrderDetailVo> delList = dbList.stream()
            .filter(it -> !ids.contains(it.getId()))
            .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(delList)) {
            salesOrderDetailService.deleteByIds(delList.stream().map(SalesOrderDetailVo::getId).collect(Collectors.toList()));
        }
    }

    /**
     * 保存明细列表
     */
    private void saveDetails(Long orderId, List<SalesOrderDetailBo> details) {
        if (CollUtil.isEmpty(details)) {
            return;
        }
        List<SalesOrderDetail> detailList = MapstructUtils.convert(details, SalesOrderDetail.class);
        detailList.forEach(it -> it.setOrderId(orderId));
        salesOrderDetailService.saveDetails(detailList);
    }

    /**
     * 提交审批：状态0→1
     */
    @Transactional
    public void submitForApproval(Long id) {
        SalesOrderVo vo = queryById(id);
        Assert.notNull(vo, "销售订单不存在");
        if (!Integer.valueOf(0).equals(vo.getOrderStatus())) {
            throw new BaseException("只有待审批状态的订单可以提交审批");
        }
        LambdaUpdateWrapper<SalesOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SalesOrder::getId, id);
        wrapper.set(SalesOrder::getOrderStatus, 1);
        salesOrderMapper.update(null, wrapper);
    }

    /**
     * 审批通过：状态1→2(生产中)
     */
    @Transactional
    public void approve(Long id) {
        SalesOrderVo vo = queryById(id);
        Assert.notNull(vo, "销售订单不存在");
        if (!Integer.valueOf(1).equals(vo.getOrderStatus())) {
            throw new BaseException("当前订单不是待审批状态，无法操作");
        }
        LambdaUpdateWrapper<SalesOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SalesOrder::getId, id);
        wrapper.set(SalesOrder::getOrderStatus, 2);
        salesOrderMapper.update(null, wrapper);
    }

    /**
     * 审批驳回：状态1→5(已取消)
     */
    @Transactional
    public void reject(Long id, String reason) {
        SalesOrderVo vo = queryById(id);
        Assert.notNull(vo, "销售订单不存在");
        if (!Integer.valueOf(1).equals(vo.getOrderStatus())) {
            throw new BaseException("当前订单不是待审批状态，无法操作");
        }
        LambdaUpdateWrapper<SalesOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SalesOrder::getId, id);
        wrapper.set(SalesOrder::getOrderStatus, 5);
        wrapper.set(SalesOrder::getRemark, reason);
        salesOrderMapper.update(null, wrapper);
    }

    /**
     * 开始生产：状态2→2(保持生产中,记录时间)
     */
    @Transactional
    public void startProduction(Long id) {
        SalesOrderVo vo = queryById(id);
        Assert.notNull(vo, "销售订单不存在");
        if (!Integer.valueOf(2).equals(vo.getOrderStatus())) {
            throw new BaseException("当前订单不是生产中状态，无法开始生产");
        }
        LambdaUpdateWrapper<SalesOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SalesOrder::getId, id);
        wrapper.set(SalesOrder::getOrderStatus, 2);
        wrapper.set(SalesOrder::getProductionTime, LocalDateTime.now());
        salesOrderMapper.update(null, wrapper);
    }

    /**
     * 发货：状态2→3(已发货)
     */
    @Transactional
    public void ship(Long id) {
        SalesOrderVo vo = queryById(id);
        Assert.notNull(vo, "销售订单不存在");
        if (!Integer.valueOf(2).equals(vo.getOrderStatus())) {
            throw new BaseException("当前订单不是生产中状态，无法发货");
        }
        LambdaUpdateWrapper<SalesOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SalesOrder::getId, id);
        wrapper.set(SalesOrder::getOrderStatus, 3);
        wrapper.set(SalesOrder::getShipTime, LocalDateTime.now());
        salesOrderMapper.update(null, wrapper);
    }

    /**
     * 完成：状态3→4(已完成)
     */
    @Transactional
    public void complete(Long id) {
        SalesOrderVo vo = queryById(id);
        Assert.notNull(vo, "销售订单不存在");
        if (!Integer.valueOf(3).equals(vo.getOrderStatus())) {
            throw new BaseException("当前订单不是已发货状态，无法完成");
        }
        LambdaUpdateWrapper<SalesOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SalesOrder::getId, id);
        wrapper.set(SalesOrder::getOrderStatus, 4);
        wrapper.set(SalesOrder::getCompleteTime, LocalDateTime.now());
        salesOrderMapper.update(null, wrapper);
    }

    /**
     * 取消：仅待审批可取消 0→5
     */
    @Transactional
    public void cancel(Long id) {
        SalesOrderVo vo = queryById(id);
        Assert.notNull(vo, "销售订单不存在");
        if (!Integer.valueOf(0).equals(vo.getOrderStatus())) {
            throw new BaseException("只有待审批状态的订单可以取消");
        }
        LambdaUpdateWrapper<SalesOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SalesOrder::getId, id);
        wrapper.set(SalesOrder::getOrderStatus, 5);
        salesOrderMapper.update(null, wrapper);
    }

    /**
     * 删除：仅待审批或已取消可删除
     */
    public void deleteById(Long id) {
        SalesOrderVo vo = queryById(id);
        Assert.notNull(vo, "销售订单不存在");
        Integer status = vo.getOrderStatus();
        if (!Integer.valueOf(0).equals(status) && !Integer.valueOf(5).equals(status)) {
            throw new ServiceException("删除失败", "只有待审批或已取消的订单才能删除");
        }
        salesOrderDetailService.deleteByOrderId(id);
        salesOrderMapper.deleteById(id);
    }

    /**
     * 编号唯一性校验
     */
    public void validateOrderNo(String orderNo, Long excludeId) {
        LambdaQueryWrapper<SalesOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(SalesOrder::getOrderNo, orderNo);
        if (excludeId != null) {
            lqw.ne(SalesOrder::getId, excludeId);
        }
        SalesOrder exist = salesOrderMapper.selectOne(lqw);
        Assert.isNull(exist, "订单编号重复，请手动修改");
    }

    /**
     * 根据订单编号查询ID
     */
    public Long queryIdByOrderNo(String orderNo) {
        LambdaQueryWrapper<SalesOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(SalesOrder::getOrderNo, orderNo);
        SalesOrder order = salesOrderMapper.selectOne(lqw);
        return order != null ? order.getId() : null;
    }

    /**
     * 从明细计算总数量和总金额
     */
    public void calculateOrderSummary(SalesOrderBo bo) {
        List<SalesOrderDetailBo> details = bo.getDetails();
        if (CollUtil.isEmpty(details)) {
            bo.setTotalQuantity(BigDecimal.ZERO);
            bo.setTotalAmount(BigDecimal.ZERO);
            return;
        }
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (SalesOrderDetailBo detail : details) {
            BigDecimal quantity = detail.getQuantity() != null ? detail.getQuantity() : BigDecimal.ZERO;
            BigDecimal unitPrice = detail.getUnitPrice() != null ? detail.getUnitPrice() : BigDecimal.ZERO;
            totalQuantity = totalQuantity.add(quantity);
            totalAmount = totalAmount.add(quantity.multiply(unitPrice));
        }
        bo.setTotalQuantity(totalQuantity);
        bo.setTotalAmount(totalAmount);
    }

}
