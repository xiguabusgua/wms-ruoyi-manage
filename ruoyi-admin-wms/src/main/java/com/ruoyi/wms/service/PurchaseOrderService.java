package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.base.BaseException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.PurchaseOrderBo;
import com.ruoyi.wms.domain.bo.PurchaseOrderDetailBo;
import com.ruoyi.wms.domain.entity.PurchaseOrder;
import com.ruoyi.wms.domain.entity.PurchaseOrderDetail;
import com.ruoyi.wms.domain.vo.PurchaseOrderDetailVo;
import com.ruoyi.wms.domain.vo.PurchaseOrderVo;
import com.ruoyi.wms.mapper.PurchaseOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 采购订单Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class PurchaseOrderService {

    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseOrderDetailService purchaseOrderDetailService;

    /**
     * 查询采购订单（含明细）
     */
    public PurchaseOrderVo queryById(Long id) {
        PurchaseOrderVo vo = purchaseOrderMapper.selectVoById(id);
        Assert.notNull(vo, "采购订单不存在");
        vo.setDetails(purchaseOrderDetailService.queryByOrderId(id));
        return vo;
    }

    /**
     * 查询采购订单列表
     */
    public TableDataInfo<PurchaseOrderVo> queryPageList(PurchaseOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PurchaseOrder> lqw = buildQueryWrapper(bo);
        Page<PurchaseOrderVo> result = purchaseOrderMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询采购订单列表
     */
    public List<PurchaseOrderVo> queryList(PurchaseOrderBo bo) {
        LambdaQueryWrapper<PurchaseOrder> lqw = buildQueryWrapper(bo);
        return purchaseOrderMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PurchaseOrder> buildQueryWrapper(PurchaseOrderBo bo) {
        LambdaQueryWrapper<PurchaseOrder> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getOrderNo()), PurchaseOrder::getOrderNo, bo.getOrderNo());
        lqw.eq(bo.getSupplierId() != null, PurchaseOrder::getSupplierId, bo.getSupplierId());
        lqw.eq(bo.getOrderStatus() != null, PurchaseOrder::getOrderStatus, bo.getOrderStatus());
        lqw.eq(bo.getWarehouseId() != null, PurchaseOrder::getWarehouseId, bo.getWarehouseId());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增采购订单
     */
    @Transactional
    public Long insertByBo(PurchaseOrderBo bo) {
        validateOrderNo(bo.getOrderNo());
        bo.setOrderStatus(0);
        calculateTotalAmount(bo);
        PurchaseOrder add = MapstructUtils.convert(bo, PurchaseOrder.class);
        purchaseOrderMapper.insert(add);
        bo.setId(add.getId());
        saveDetails(add.getId(), bo.getDetails());
        return add.getId();
    }

    /**
     * 修改采购订单
     */
    @Transactional
    public void updateByBo(PurchaseOrderBo bo) {
        PurchaseOrder update = MapstructUtils.convert(bo, PurchaseOrder.class);
        purchaseOrderMapper.updateById(update);
        handleDetailChanges(bo.getId(), bo.getDetails());
        List<PurchaseOrderDetail> detailList = MapstructUtils.convert(bo.getDetails(), PurchaseOrderDetail.class);
        detailList.forEach(it -> it.setOrderId(bo.getId()));
        purchaseOrderDetailService.saveDetails(detailList);
    }

    private void handleDetailChanges(Long orderId, List<PurchaseOrderDetailBo> details) {
        if (details == null) {
            return;
        }
        List<PurchaseOrderDetailVo> dbList = purchaseOrderDetailService.queryByOrderId(orderId);
        var ids = details.stream()
            .filter(it -> it.getId() != null)
            .map(it -> it.getId())
            .toList();
        var delList = dbList.stream()
            .filter(it -> !ids.contains(it.getId()))
            .toList();
        if (CollUtil.isNotEmpty(delList)) {
            purchaseOrderDetailService.deleteByIds(delList.stream().map(PurchaseOrderDetailVo::getId).toList());
        }
    }

    /**
     * 审批通过
     */
    @Transactional
    public void approve(Long id) {
        PurchaseOrderVo vo = queryById(id);
        Assert.notNull(vo, "采购订单不存在");
        if (vo.getOrderStatus() == null || vo.getOrderStatus() != 0) {
            throw new BaseException("当前采购订单状态不是待审批，无法操作");
        }
        LambdaUpdateWrapper<PurchaseOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseOrder::getId, id);
        wrapper.set(PurchaseOrder::getOrderStatus, 1);
        purchaseOrderMapper.update(null, wrapper);
    }

    /**
     * 部分到货
     */
    @Transactional
    public void partialReceive(Long id) {
        LambdaUpdateWrapper<PurchaseOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseOrder::getId, id);
        wrapper.in(PurchaseOrder::getOrderStatus, 1, 2);
        wrapper.set(PurchaseOrder::getOrderStatus, 2);
        purchaseOrderMapper.update(null, wrapper);
    }

    /**
     * 全部到货
     */
    @Transactional
    public void fullReceive(Long id) {
        LambdaUpdateWrapper<PurchaseOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseOrder::getId, id);
        wrapper.in(PurchaseOrder::getOrderStatus, 1, 2);
        wrapper.set(PurchaseOrder::getOrderStatus, 3);
        purchaseOrderMapper.update(null, wrapper);
    }

    /**
     * 关闭采购订单
     */
    @Transactional
    public void closeOrder(Long id) {
        LambdaUpdateWrapper<PurchaseOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseOrder::getId, id);
        wrapper.notIn(PurchaseOrder::getOrderStatus, 4, 5);
        wrapper.set(PurchaseOrder::getOrderStatus, 4);
        purchaseOrderMapper.update(null, wrapper);
    }

    /**
     * 取消采购订单
     */
    @Transactional
    public void cancelOrder(Long id) {
        LambdaUpdateWrapper<PurchaseOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseOrder::getId, id);
        wrapper.in(PurchaseOrder::getOrderStatus, 0, 1);
        wrapper.set(PurchaseOrder::getOrderStatus, 5);
        purchaseOrderMapper.update(null, wrapper);
    }

    /**
     * 删除采购订单
     */
    @Transactional
    public void deleteById(Long id) {
        validateBeforeDelete(id);
        purchaseOrderDetailService.deleteByOrderId(id);
        purchaseOrderMapper.deleteById(id);
    }

    private void validateBeforeDelete(Long id) {
        PurchaseOrderVo vo = queryById(id);
        Assert.notNull(vo, "采购订单不存在");
        Integer status = vo.getOrderStatus();
        if (status != null && (status == 1 || status == 2)) {
            throw new BaseException("采购订单【" + vo.getOrderNo() + "】已审批或部分到货，无法删除！");
        }
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(this::deleteById);
    }

    public void validateOrderNo(String orderNo) {
        LambdaQueryWrapper<PurchaseOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseOrder::getOrderNo, orderNo);
        PurchaseOrder exist = purchaseOrderMapper.selectOne(lqw);
        Assert.isNull(exist, "订单编号重复，请手动修改");
    }

    /**
     * 计算总金额
     */
    private void calculateTotalAmount(PurchaseOrderBo bo) {
        List<PurchaseOrderDetailBo> details = bo.getDetails();
        if (CollUtil.isEmpty(details)) {
            bo.setTotalAmount(BigDecimal.ZERO);
            return;
        }
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PurchaseOrderDetailBo detail : details) {
            BigDecimal qty = detail.getQuantity() != null ? detail.getQuantity() : BigDecimal.ZERO;
            BigDecimal price = detail.getUnitPrice() != null ? detail.getUnitPrice() : BigDecimal.ZERO;
            totalAmount = totalAmount.add(qty.multiply(price));
        }
        bo.setTotalAmount(totalAmount);
    }

    private void saveDetails(Long orderId, List<PurchaseOrderDetailBo> details) {
        if (CollUtil.isEmpty(details)) {
            return;
        }
        List<PurchaseOrderDetail> detailList = MapstructUtils.convert(details, PurchaseOrderDetail.class);
        detailList.forEach(it -> it.setOrderId(orderId));
        purchaseOrderDetailService.saveDetails(detailList);
    }

}
