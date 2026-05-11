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
import com.ruoyi.wms.domain.bo.PurchaseRequisitionBo;
import com.ruoyi.wms.domain.bo.PurchaseRequisitionDetailBo;
import com.ruoyi.wms.domain.entity.PurchaseRequisition;
import com.ruoyi.wms.domain.entity.PurchaseRequisitionDetail;
import com.ruoyi.wms.domain.vo.PurchaseRequisitionDetailVo;
import com.ruoyi.wms.domain.vo.PurchaseRequisitionVo;
import com.ruoyi.wms.mapper.PurchaseRequisitionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 采购申请Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class PurchaseRequisitionService {

    private final PurchaseRequisitionMapper purchaseRequisitionMapper;
    private final PurchaseRequisitionDetailService purchaseRequisitionDetailService;

    /**
     * 查询采购申请（含明细）
     */
    public PurchaseRequisitionVo queryById(Long id) {
        PurchaseRequisitionVo vo = purchaseRequisitionMapper.selectVoById(id);
        Assert.notNull(vo, "采购申请不存在");
        vo.setDetails(purchaseRequisitionDetailService.queryByRequisitionId(id));
        return vo;
    }

    /**
     * 查询采购申请列表
     */
    public TableDataInfo<PurchaseRequisitionVo> queryPageList(PurchaseRequisitionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PurchaseRequisition> lqw = buildQueryWrapper(bo);
        Page<PurchaseRequisitionVo> result = purchaseRequisitionMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询采购申请列表
     */
    public List<PurchaseRequisitionVo> queryList(PurchaseRequisitionBo bo) {
        LambdaQueryWrapper<PurchaseRequisition> lqw = buildQueryWrapper(bo);
        return purchaseRequisitionMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PurchaseRequisition> buildQueryWrapper(PurchaseRequisitionBo bo) {
        LambdaQueryWrapper<PurchaseRequisition> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getRequisitionNo()), PurchaseRequisition::getRequisitionNo, bo.getRequisitionNo());
        lqw.eq(bo.getRequisitionType() != null, PurchaseRequisition::getRequisitionType, bo.getRequisitionType());
        lqw.eq(bo.getApprovalStatus() != null, PurchaseRequisition::getApprovalStatus, bo.getApprovalStatus());
        lqw.eq(bo.getUrgentLevel() != null, PurchaseRequisition::getUrgentLevel, bo.getUrgentLevel());
        lqw.like(StringUtils.isNotBlank(bo.getApplicant()), PurchaseRequisition::getApplicant, bo.getApplicant());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增采购申请
     */
    @Transactional
    public Long insertByBo(PurchaseRequisitionBo bo) {
        validateRequisitionNo(bo.getRequisitionNo());
        bo.setApprovalStatus(0);
        calculateTotalAmount(bo);
        PurchaseRequisition add = MapstructUtils.convert(bo, PurchaseRequisition.class);
        purchaseRequisitionMapper.insert(add);
        bo.setId(add.getId());
        saveDetails(add.getId(), bo.getDetails());
        return add.getId();
    }

    /**
     * 修改采购申请
     */
    @Transactional
    public void updateByBo(PurchaseRequisitionBo bo) {
        PurchaseRequisition update = MapstructUtils.convert(bo, PurchaseRequisition.class);
        purchaseRequisitionMapper.updateById(update);
        handleDetailChanges(bo.getId(), bo.getDetails());
        List<PurchaseRequisitionDetail> detailList = MapstructUtils.convert(bo.getDetails(), PurchaseRequisitionDetail.class);
        detailList.forEach(it -> it.setRequisitionId(bo.getId()));
        purchaseRequisitionDetailService.saveDetails(detailList);
    }

    private void handleDetailChanges(Long requisitionId, List<PurchaseRequisitionDetailBo> details) {
        if (details == null) {
            return;
        }
        List<PurchaseRequisitionDetailVo> dbList = purchaseRequisitionDetailService.queryByRequisitionId(requisitionId);
        var ids = details.stream()
            .filter(it -> it.getId() != null)
            .map(it -> it.getId())
            .toList();
        var delList = dbList.stream()
            .filter(it -> !ids.contains(it.getId()))
            .toList();
        if (CollUtil.isNotEmpty(delList)) {
            purchaseRequisitionDetailService.deleteByIds(delList.stream().map(PurchaseRequisitionDetailVo::getId).toList());
        }
    }

    /**
     * 提交审批
     */
    @Transactional
    public void submitForApproval(Long id) {
        PurchaseRequisitionVo vo = queryById(id);
        Assert.notNull(vo, "采购申请不存在");
        if (vo.getApprovalStatus() != null && vo.getApprovalStatus() != 0 && vo.getApprovalStatus() != 2) {
            throw new BaseException("只有待审批或已驳回状态的采购申请可以提交");
        }
        LambdaUpdateWrapper<PurchaseRequisition> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseRequisition::getId, id);
        wrapper.set(PurchaseRequisition::getApprovalStatus, 0);
        purchaseRequisitionMapper.update(null, wrapper);
    }

    /**
     * 审批通过
     */
    @Transactional
    public void approve(Long id, Long approverId, String approveRemark) {
        PurchaseRequisitionVo vo = queryById(id);
        Assert.notNull(vo, "采购申请不存在");
        if (vo.getApprovalStatus() == null || vo.getApprovalStatus() != 0) {
            throw new BaseException("当前采购申请状态不是待审批，无法操作");
        }
        LambdaUpdateWrapper<PurchaseRequisition> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseRequisition::getId, id);
        wrapper.set(PurchaseRequisition::getApprovalStatus, 1);
        wrapper.set(PurchaseRequisition::getApproverId, approverId);
        wrapper.set(PurchaseRequisition::getApproveTime, LocalDateTime.now());
        wrapper.set(PurchaseRequisition::getApproveRemark, approveRemark);
        purchaseRequisitionMapper.update(null, wrapper);
    }

    /**
     * 审批驳回
     */
    @Transactional
    public void reject(Long id, Long approverId, String rejectReason) {
        PurchaseRequisitionVo vo = queryById(id);
        Assert.notNull(vo, "采购申请不存在");
        if (vo.getApprovalStatus() == null || vo.getApprovalStatus() != 0) {
            throw new BaseException("当前采购申请状态不是待审批，无法操作");
        }
        LambdaUpdateWrapper<PurchaseRequisition> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseRequisition::getId, id);
        wrapper.set(PurchaseRequisition::getApprovalStatus, 2);
        wrapper.set(PurchaseRequisition::getApproverId, approverId);
        wrapper.set(PurchaseRequisition::getApproveTime, LocalDateTime.now());
        wrapper.set(PurchaseRequisition::getApproveRemark, rejectReason);
        purchaseRequisitionMapper.update(null, wrapper);
    }

    /**
     * 关闭采购申请
     */
    @Transactional
    public void closeRequisition(Long id) {
        LambdaUpdateWrapper<PurchaseRequisition> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PurchaseRequisition::getId, id);
        wrapper.ne(PurchaseRequisition::getApprovalStatus, 3);
        wrapper.set(PurchaseRequisition::getApprovalStatus, 3);
        purchaseRequisitionMapper.update(null, wrapper);
    }

    /**
     * 删除采购申请
     */
    @Transactional
    public void deleteById(Long id) {
        validateBeforeDelete(id);
        purchaseRequisitionDetailService.deleteByRequisitionId(id);
        purchaseRequisitionMapper.deleteById(id);
    }

    private void validateBeforeDelete(Long id) {
        PurchaseRequisitionVo vo = queryById(id);
        Assert.notNull(vo, "采购申请不存在");
        Integer status = vo.getApprovalStatus();
        if (status != null && status == 1) {
            throw new BaseException("采购申请【" + vo.getRequisitionNo() + "】已审批通过，无法删除！");
        }
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(this::deleteById);
    }

    public void validateRequisitionNo(String requisitionNo) {
        LambdaQueryWrapper<PurchaseRequisition> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseRequisition::getRequisitionNo, requisitionNo);
        PurchaseRequisition exist = purchaseRequisitionMapper.selectOne(lqw);
        Assert.isNull(exist, "申请编号重复，请手动修改");
    }

    /**
     * 计算总金额
     */
    private void calculateTotalAmount(PurchaseRequisitionBo bo) {
        List<PurchaseRequisitionDetailBo> details = bo.getDetails();
        if (CollUtil.isEmpty(details)) {
            bo.setTotalAmount(BigDecimal.ZERO);
            return;
        }
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PurchaseRequisitionDetailBo detail : details) {
            BigDecimal qty = detail.getQuantity() != null ? detail.getQuantity() : BigDecimal.ZERO;
            BigDecimal price = detail.getUnitPrice() != null ? detail.getUnitPrice() : BigDecimal.ZERO;
            detail.getAmount(qty.multiply(price));
            totalAmount = totalAmount.add(qty.multiply(price));
        }
        bo.setTotalAmount(totalAmount);
    }

    private void saveDetails(Long requisitionId, List<PurchaseRequisitionDetailBo> details) {
        if (CollUtil.isEmpty(details)) {
            return;
        }
        List<PurchaseRequisitionDetail> detailList = MapstructUtils.convert(details, PurchaseRequisitionDetail.class);
        detailList.forEach(it -> it.setRequisitionId(requisitionId));
        purchaseRequisitionDetailService.saveDetails(detailList);
    }

}
