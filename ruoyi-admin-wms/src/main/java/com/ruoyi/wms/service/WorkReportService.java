package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.WorkReportBo;
import com.ruoyi.wms.domain.entity.WorkOrder;
import com.ruoyi.wms.domain.entity.WorkReport;
import com.ruoyi.wms.domain.vo.WorkReportVo;
import com.ruoyi.wms.mapper.WorkOrderMapper;
import com.ruoyi.wms.mapper.WorkReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 报工记录Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class WorkReportService {

    private final WorkReportMapper workReportMapper;
    private final WorkOrderMapper workOrderMapper;

    /**
     * 报工类型常量
     */
    public static class ReportType {
        /** 开工 */
        public static final Integer START = 1;
        /** 完工 */
        public static final Integer COMPLETE = 2;
    }

    /**
     * 查询报工记录
     */
    public WorkReportVo queryById(Long id) {
        return workReportMapper.selectVoById(id);
    }

    /**
     * 查询报工记录列表
     */
    public TableDataInfo<WorkReportVo> queryPageList(WorkReportBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WorkReport> lqw = buildQueryWrapper(bo);
        Page<WorkReportVo> result = workReportMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询报工记录列表
     */
    public java.util.List<WorkReportVo> queryList(WorkReportBo bo) {
        LambdaQueryWrapper<WorkReport> lqw = buildQueryWrapper(bo);
        return workReportMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WorkReport> buildQueryWrapper(WorkReportBo bo) {
        LambdaQueryWrapper<WorkReport> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getWorkOrderId() != null, WorkReport::getWorkOrderId, bo.getWorkOrderId());
        lqw.eq(bo.getReportType() != null, WorkReport::getReportType, bo.getReportType());
        lqw.like(StringUtils.isNotBlank(bo.getOperator()), WorkReport::getOperator, bo.getOperator());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增报工记录
     */
    @Transactional
    public Long insertByBo(WorkReportBo bo) {
        validateReport(bo);
        if (bo.getQuantity() == null) {
            bo.setQuantity(BigDecimal.ZERO);
        }
        if (bo.getDefectiveQuantity() == null) {
            bo.setDefectiveQuantity(BigDecimal.ZERO);
        }
        if (bo.getReportTime() == null) {
            bo.setReportTime(LocalDateTime.now());
        }
        WorkReport add = MapstructUtils.convert(bo, WorkReport.class);
        workReportMapper.insert(add);
        return add.getId();
    }

    /**
     * 开工报工 - 自动触发工单状态为生产中
     */
    @Transactional
    public Long startReport(Long workOrderId, String operator, String remark) {
        Assert.notNull(workOrderId, "工单ID不能为空");
        WorkOrder workOrder = workOrderMapper.selectById(workOrderId);
        Assert.notNull(workOrder, "工单不存在");
        if (WorkOrderService.WorkStatus.PENDING.equals(workOrder.getWorkStatus())
            || WorkOrderService.WorkStatus.PAUSED.equals(workOrder.getWorkStatus())) {
            LambdaUpdateWrapper<WorkOrder> wrapper = Wrappers.lambdaUpdate();
            wrapper.eq(WorkOrder::getId, workOrderId);
            wrapper.set(WorkOrder::getWorkStatus, WorkOrderService.WorkStatus.IN_PROGRESS);
            if (workOrder.getActualStartTime() == null) {
                wrapper.set(WorkOrder::getActualStartTime, LocalDateTime.now());
            }
            workOrderMapper.update(null, wrapper);
        }
        WorkReport report = new WorkReport();
        report.setWorkOrderId(workOrderId);
        report.setReportType(ReportType.START);
        report.setReportTime(LocalDateTime.now());
        report.setQuantity(BigDecimal.ZERO);
        report.setDefectiveQuantity(BigDecimal.ZERO);
        report.setOperator(operator);
        report.setRemark(remark);
        workReportMapper.insert(report);
        return report.getId();
    }

    /**
     * 完工报工 - 更新工单完工数量和不良数量，自动完成工单
     */
    @Transactional
    public Long completeReport(Long workOrderId, BigDecimal quantity, BigDecimal defectiveQuantity,
                                String operator, String remark) {
        Assert.notNull(workOrderId, "工单ID不能为空");
        WorkOrder workOrder = workOrderMapper.selectById(workOrderId);
        Assert.notNull(workOrder, "工单不存在");
        BigDecimal completedQty = workOrder.getCompletedQuantity() != null ? workOrder.getCompletedQuantity() : BigDecimal.ZERO;
        BigDecimal defectiveQty = workOrder.getDefectiveQuantity() != null ? workOrder.getDefectiveQuantity() : BigDecimal.ZERO;
        completedQty = completedQty.add(quantity != null ? quantity : BigDecimal.ZERO);
        defectiveQty = defectiveQty.add(defectiveQuantity != null ? defectiveQuantity : BigDecimal.ZERO);
        LambdaUpdateWrapper<WorkOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WorkOrder::getId, workOrderId);
        wrapper.set(WorkOrder::getCompletedQuantity, completedQty);
        wrapper.set(WorkOrder::getDefectiveQuantity, defectiveQty);
        wrapper.set(WorkOrder::getWorkStatus, WorkOrderService.WorkStatus.COMPLETED);
        wrapper.set(WorkOrder::getActualEndTime, LocalDateTime.now());
        workOrderMapper.update(null, wrapper);
        WorkReport report = new WorkReport();
        report.setWorkOrderId(workOrderId);
        report.setReportType(ReportType.COMPLETE);
        report.setReportTime(LocalDateTime.now());
        report.setQuantity(quantity != null ? quantity : BigDecimal.ZERO);
        report.setDefectiveQuantity(defectiveQuantity != null ? defectiveQuantity : BigDecimal.ZERO);
        report.setOperator(operator);
        report.setRemark(remark);
        workReportMapper.insert(report);
        return report.getId();
    }

    /**
     * 修改报工记录
     */
    @Transactional
    public void updateByBo(WorkReportBo bo) {
        WorkReport update = MapstructUtils.convert(bo, WorkReport.class);
        workReportMapper.updateById(update);
    }

    /**
     * 删除报工记录
     */
    public void deleteById(Long id) {
        workReportMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        workReportMapper.deleteByIds(ids);
    }

    /**
     * 校验报工数据
     */
    private void validateReport(WorkReportBo bo) {
        WorkOrder workOrder = workOrderMapper.selectById(bo.getWorkOrderId());
        Assert.notNull(workOrder, "关联的工单不存在");
        if (ReportType.START.equals(bo.getReportType())) {
            if (!WorkOrderService.WorkStatus.PENDING.equals(workOrder.getWorkStatus())
                && !WorkOrderService.WorkStatus.PAUSED.equals(workOrder.getWorkStatus())) {
                throw new ServiceException("当前工单状态不允许开工报工");
            }
        } else if (ReportType.COMPLETE.equals(bo.getReportType())) {
            if (!WorkOrderService.WorkStatus.IN_PROGRESS.equals(workOrder.getWorkStatus())
                && !WorkOrderService.WorkStatus.PAUSED.equals(workOrder.getWorkStatus())) {
                throw new ServiceException("当前工单状态不允许完工报工");
            }
        }
    }

}
