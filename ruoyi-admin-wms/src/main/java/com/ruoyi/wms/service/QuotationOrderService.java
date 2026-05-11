package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.common.core.constant.ServiceConstants;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.exception.base.BaseException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.QuotationCostBo;
import com.ruoyi.wms.domain.bo.QuotationOrderBo;
import com.ruoyi.wms.domain.bo.QuotationOrderDetailBo;
import com.ruoyi.wms.domain.entity.QuotationCost;
import com.ruoyi.wms.domain.entity.QuotationOrder;
import com.ruoyi.wms.domain.entity.QuotationOrderDetail;
import com.ruoyi.wms.domain.vo.QuotationCostVo;
import com.ruoyi.wms.domain.vo.QuotationOrderDetailVo;
import com.ruoyi.wms.domain.vo.QuotationOrderVo;
import com.ruoyi.wms.mapper.QuotationOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 报价单Service业务层处理
 *
 * @author zcc
 */
@RequiredArgsConstructor
@Service
public class QuotationOrderService {

    private final QuotationOrderMapper quotationOrderMapper;
    private final QuotationOrderDetailService quotationOrderDetailService;
    private final QuotationCostService quotationCostService;

    /**
     * 查询报价单（含明细）
     */
    public QuotationOrderVo queryById(Long id) {
        QuotationOrderVo vo = quotationOrderMapper.selectVoById(id);
        Assert.notNull(vo, "报价单不存在");
        vo.setDetails(quotationOrderDetailService.queryByQuotationOrderId(id));
        return vo;
    }

    /**
     * 查询报价单列表
     */
    public TableDataInfo<QuotationOrderVo> queryPageList(QuotationOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<QuotationOrder> lqw = buildQueryWrapper(bo);
        Page<QuotationOrderVo> result = quotationOrderMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询报价单历史记录（按客户查询）
     */
    public TableDataInfo<QuotationOrderVo> queryHistoryList(QuotationOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<QuotationOrder> lqw = buildHistoryQueryWrapper(bo);
        Page<QuotationOrderVo> result = quotationOrderMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询报价单列表
     */
    public List<QuotationOrderVo> queryList(QuotationOrderBo bo) {
        LambdaQueryWrapper<QuotationOrder> lqw = buildQueryWrapper(bo);
        return quotationOrderMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<QuotationOrder> buildQueryWrapper(QuotationOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<QuotationOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNo()), QuotationOrder::getOrderNo, bo.getOrderNo());
        lqw.eq(bo.getCustomerId() != null, QuotationOrder::getCustomerId, bo.getCustomerId());
        lqw.eq(bo.getOrderStatus() != null, QuotationOrder::getOrderStatus, bo.getOrderStatus());
        lqw.eq(bo.getTotalQuoteAmount() != null, QuotationOrder::getTotalQuoteAmount, bo.getTotalQuoteAmount());
        lqw.like(StringUtils.isNotBlank(bo.getContactPerson()), QuotationOrder::getContactPerson, bo.getContactPerson());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    private LambdaQueryWrapper<QuotationOrder> buildHistoryQueryWrapper(QuotationOrderBo bo) {
        LambdaQueryWrapper<QuotationOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCustomerId() != null, QuotationOrder::getCustomerId, bo.getCustomerId());
        lqw.in(Arrays.asList(
            ServiceConstants.QuotationOrderStatus.APPROVED,
            ServiceConstants.QuotationOrderStatus.CONVERTED,
            ServiceConstants.QuotationOrderStatus.EXPIRED
        ).contains(bo.getOrderStatus()), QuotationOrder::getOrderStatus, bo.getOrderStatus());
        lqw.ne(QuotationOrder::getOrderStatus, ServiceConstants.QuotationOrderStatus.DRAFT);
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * F2.2.1 报价单录入 - 保存草稿/暂存
     */
    @Transactional
    public Long insertByBo(QuotationOrderBo bo) {
        validateOrderNo(bo.getOrderNo());
        bo.setOrderStatus(ServiceConstants.QuotationOrderStatus.DRAFT);
        calculateOrderSummary(bo);
        QuotationOrder add = MapstructUtils.convert(bo, QuotationOrder.class);
        quotationOrderMapper.insert(add);
        bo.setId(add.getId());
        saveDetails(add.getId(), bo.getDetails());
        return add.getId();
    }

    /**
     * F2.2.1 报价单录入 - 提交审批
     */
    @Transactional
    public void submitForApproval(Long id) {
        QuotationOrderVo vo = queryById(id);
        Assert.notNull(vo, "报价单不存在");
        if (!ServiceConstants.QuotationOrderStatus.DRAFT.equals(vo.getOrderStatus())
            && !ServiceConstants.QuotationOrderStatus.REJECTED.equals(vo.getOrderStatus())) {
            throw new BaseException("只有草稿或已驳回状态的报价单可以提交审批");
        }
        if (CollUtil.isEmpty(vo.getDetails())) {
            throw new BaseException("报价明细不能为空，请添加产品后提交");
        }
        LambdaUpdateWrapper<QuotationOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(QuotationOrder::getId, id);
        wrapper.set(QuotationOrder::getOrderStatus, ServiceConstants.QuotationOrderStatus.PENDING_APPROVAL);
        quotationOrderMapper.update(null, wrapper);
    }

    /**
     * F2.2.3 报价审批 - 审批通过
     */
    @Transactional
    public void approve(Long id, Long approverId, String approveRemark) {
        QuotationOrderVo vo = queryById(id);
        Assert.notNull(vo, "报价单不存在");
        if (!ServiceConstants.QuotationOrderStatus.PENDING_APPROVAL.equals(vo.getOrderStatus())) {
            throw new BaseException("当前报价单状态不是待审批，无法操作");
        }
        LambdaUpdateWrapper<QuotationOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(QuotationOrder::getId, id);
        wrapper.set(QuotationOrder::getOrderStatus, ServiceConstants.QuotationOrderStatus.APPROVED);
        wrapper.set(QuotationOrder::getApproverId, approverId);
        wrapper.set(QuotationOrder::getApproveTime, LocalDateTime.now());
        wrapper.set(QuotationOrder::getApproveRemark, approveRemark);
        quotationOrderMapper.update(null, wrapper);
    }

    /**
     * F2.2.3 报价审批 - 审批驳回
     */
    @Transactional
    public void reject(Long id, Long approverId, String rejectReason) {
        QuotationOrderVo vo = queryById(id);
        Assert.notNull(vo, "报价单不存在");
        if (!ServiceConstants.QuotationOrderStatus.PENDING_APPROVAL.equals(vo.getOrderStatus())) {
            throw new BaseException("当前报价单状态不是待审批，无法操作");
        }
        LambdaUpdateWrapper<QuotationOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(QuotationOrder::getId, id);
        wrapper.set(QuotationOrder::getOrderStatus, ServiceConstants.QuotationOrderStatus.REJECTED);
        wrapper.set(QuotationOrder::getApproverId, approverId);
        wrapper.set(QuotationOrder::getApproveTime, LocalDateTime.now());
        wrapper.set(QuotationOrder::getApproveRemark, rejectReason);
        quotationOrderMapper.update(null, wrapper);
    }

    /**
     * F2.2.4 报价转订单 - 将已审批通过的报价单转为销售出库订单
     */
    @Transactional
    public void convertToShipmentOrder(Long id) {
        QuotationOrderVo vo = queryById(id);
        Assert.notNull(vo, "报价单不存在");
        if (!ServiceConstants.QuotationOrderStatus.APPROVED.equals(vo.getOrderStatus())) {
            throw new BaseException("只有审批通过的报价单才能转为订单");
        }
        LambdaUpdateWrapper<QuotationOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(QuotationOrder::getId, id);
        wrapper.set(QuotationOrder::getOrderStatus, ServiceConstants.QuotationOrderStatus.CONVERTED);
        quotationOrderMapper.update(null, wrapper);
    }

    /**
     * 标记报价单为已失效（超过有效期）
     */
    @Transactional
    public void markExpired(Long id) {
        LambdaUpdateWrapper<QuotationOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(QuotationOrder::getId, id);
        wrapper.eq(QuotationOrder::getOrderStatus, ServiceConstants.QuotationOrderStatus.APPROVED);
        wrapper.set(QuotationOrder::getOrderStatus, ServiceConstants.QuotationOrderStatus.EXPIRED);
        quotationOrderMapper.update(null, wrapper);
    }

    /**
     * F2.2.2 成本核算 - 保存成本核算数据并自动计算总成本
     */
    @Transactional
    public void saveCostCalculation(Long quotationOrderId, List<QuotationCostBo> costBos) {
        QuotationOrderVo vo = queryById(quotationOrderId);
        Assert.notNull(vo, "报价单不存在");
        List<QuotationCost> costs = MapstructUtils.convert(costBos, QuotationCost.class);
        costs.forEach(cost -> {
            cost.setQuotationOrderId(quotationOrderId);
            BigDecimal totalCost = quotationCostService.calculateTotalCost(
                cost.getMaterialCost(), cost.getLaborCost(),
                cost.getManufacturingCost(), cost.getOtherCost()
            );
            cost.setTotalCost(totalCost);
        });
        quotationCostService.saveCosts(costs);
    }

    /**
     * 修改报价单
     */
    @Transactional
    public void updateByBo(QuotationOrderBo bo) {
        QuotationOrder update = MapstructUtils.convert(bo, QuotationOrder.class);
        quotationOrderMapper.updateById(update);
        List<QuotationOrderDetail> detailList = MapstructUtils.convert(bo.getDetails(), QuotationOrderDetail.class);
        handleDetailChanges(bo.getId(), detailList);
        detailList.forEach(it -> it.setOrderId(bo.getId()));
        quotationOrderDetailService.saveDetails(detailList);
    }

    private void handleDetailChanges(Long orderId, List<QuotationOrderDetail> detailList) {
        List<QuotationOrderDetailVo> dbList = quotationOrderDetailService.queryByQuotationOrderId(orderId);
        Set<Long> ids = detailList.stream()
            .filter(it -> it.getId() != null)
            .map(it -> it.getId())
            .collect(Collectors.toSet());
        List<QuotationOrderDetailVo> delList = dbList.stream()
            .filter(it -> !ids.contains(it.getId()))
            .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(delList)) {
            quotationOrderDetailService.deleteByIds(delList.stream().map(QuotationOrderDetailVo::getId).collect(Collectors.toList()));
        }
    }

    /**
     * 删除报价单
     */
    public void deleteById(Long id) {
        validateIdBeforeDelete(id);
        quotationOrderDetailService.deleteByQuotationOrderId(id);
        quotationCostService.deleteByQuotationOrderId(id);
        quotationOrderMapper.deleteById(id);
    }

    private void validateIdBeforeDelete(Long id) {
        QuotationOrderVo vo = queryById(id);
        Assert.notNull(vo, "报价单不存在");
        Integer status = vo.getOrderStatus();
        if (ServiceConstants.QuotationOrderStatus.PENDING_APPROVAL.equals(status)) {
            throw new ServiceException("删除失败", HttpStatus.CONFLICT, "报价单【" + vo.getOrderNo() + "】正在审批中，无法删除！");
        }
        if (ServiceConstants.QuotationOrderStatus.APPROVED.equals(status)
            || ServiceConstants.QuotationOrderStatus.CONVERTED.equals(status)) {
            throw new ServiceException("删除失败", HttpStatus.CONFLICT, "报价单【" + vo.getOrderNo() + "】已审批通过或已转订单，无法删除！");
        }
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(this::deleteById);
    }

    public void validateOrderNo(String orderNo) {
        LambdaQueryWrapper<QuotationOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(QuotationOrder::getOrderNo, orderNo);
        QuotationOrder exist = quotationOrderMapper.selectOne(lqw);
        Assert.isNull(exist, "报价单号重复，请手动修改");
    }

    public Long queryIdByOrderNo(String orderNo) {
        LambdaQueryWrapper<QuotationOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(QuotationOrder::getOrderNo, orderNo);
        QuotationOrder order = quotationOrderMapper.selectOne(lqw);
        return order != null ? order.getId() : null;
    }

    /**
     * 计算报价单汇总信息（总数量、总金额、利润率等）
     */
    private void calculateOrderSummary(QuotationOrderBo bo) {
        List<QuotationOrderDetailBo> details = bo.getDetails();
        if (CollUtil.isEmpty(details)) {
            bo.setTotalQuantity(BigDecimal.ZERO);
            bo.setTotalCostAmount(BigDecimal.ZERO);
            bo.setTotalQuoteAmount(BigDecimal.ZERO);
            return;
        }
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalCostAmount = BigDecimal.ZERO;
        BigDecimal totalQuoteAmount = BigDecimal.ZERO;
        for (QuotationOrderDetailBo detail : details) {
            totalQuantity = totalQuantity.add(detail.getQuantity() != null ? detail.getQuantity() : BigDecimal.ZERO);
            BigDecimal costSubtotal = detail.getCostSubtotal() != null ? detail.getCostSubtotal() : BigDecimal.ZERO;
            BigDecimal quoteSubtotal = detail.getQuoteSubtotal() != null ? detail.getQuoteSubtotal() : BigDecimal.ZERO;
            totalCostAmount = totalCostAmount.add(costSubtotal);
            totalQuoteAmount = totalQuoteAmount.add(quoteSubtotal);
        }
        bo.setTotalQuantity(totalQuantity);
        bo.setTotalCostAmount(totalCostAmount);
        bo.setTotalQuoteAmount(totalQuoteAmount);
        if (totalCostAmount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal profitRate = totalQuoteAmount.subtract(totalCostAmount)
                .divide(totalCostAmount, 4, java.math.RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
            bo.setProfitRate(profitRate);
        } else {
            bo.setProfitRate(BigDecimal.ZERO);
        }
    }

    private void saveDetails(Long orderId, List<QuotationOrderDetailBo> details) {
        if (CollUtil.isEmpty(details)) {
            return;
        }
        List<QuotationOrderDetail> detailList = MapstructUtils.convert(details, QuotationOrderDetail.class);
        detailList.forEach(it -> it.setOrderId(orderId));
        quotationOrderDetailService.saveDetails(detailList);
    }

}
