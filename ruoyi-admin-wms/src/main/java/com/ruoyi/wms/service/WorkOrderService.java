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
import com.ruoyi.wms.domain.bo.WorkOrderBo;
import com.ruoyi.wms.domain.entity.WorkOrder;
import com.ruoyi.wms.domain.vo.WorkOrderVo;
import com.ruoyi.wms.mapper.WorkOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 工单/派工单Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class WorkOrderService {

    private final WorkOrderMapper workOrderMapper;

    /**
     * 工单状态常量
     */
    public static class WorkStatus {
        /** 待生产 */
        public static final Integer PENDING = 0;
        /** 生产中 */
        public static final Integer IN_PROGRESS = 1;
        /** 暂停 */
        public static final Integer PAUSED = 2;
        /** 已完成 */
        public static final Integer COMPLETED = 3;
        /** 已取消 */
        public static final Integer CANCELLED = 4;
    }

    /**
     * 查询工单
     */
    public WorkOrderVo queryById(Long id) {
        return workOrderMapper.selectVoById(id);
    }

    /**
     * 查询工单列表
     */
    public TableDataInfo<WorkOrderVo> queryPageList(WorkOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WorkOrder> lqw = buildQueryWrapper(bo);
        Page<WorkOrderVo> result = workOrderMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询工单列表
     */
    public java.util.List<WorkOrderVo> queryList(WorkOrderBo bo) {
        LambdaQueryWrapper<WorkOrder> lqw = buildQueryWrapper(bo);
        return workOrderMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WorkOrder> buildQueryWrapper(WorkOrderBo bo) {
        LambdaQueryWrapper<WorkOrder> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getWorkOrderNo()), WorkOrder::getWorkOrderNo, bo.getWorkOrderNo());
        lqw.eq(bo.getPlanId() != null, WorkOrder::getPlanId, bo.getPlanId());
        lqw.eq(bo.getProductId() != null, WorkOrder::getProductId, bo.getProductId());
        lqw.eq(bo.getEquipmentId() != null, WorkOrder::getEquipmentId, bo.getEquipmentId());
        lqw.eq(bo.getMoldId() != null, WorkOrder::getMoldId, bo.getMoldId());
        lqw.eq(bo.getWorkStatus() != null, WorkOrder::getWorkStatus, bo.getWorkStatus());
        lqw.like(StringUtils.isNotBlank(bo.getOperator()), WorkOrder::getOperator, bo.getOperator());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增工单
     */
    @Transactional
    public Long insertByBo(WorkOrderBo bo) {
        validateWorkOrderNo(bo.getWorkOrderNo());
        if (bo.getCompletedQuantity() == null) {
            bo.setCompletedQuantity(BigDecimal.ZERO);
        }
        if (bo.getDefectiveQuantity() == null) {
            bo.setDefectiveQuantity(BigDecimal.ZERO);
        }
        if (bo.getWorkStatus() == null) {
            bo.setWorkStatus(WorkStatus.PENDING);
        }
        WorkOrder add = MapstructUtils.convert(bo, WorkOrder.class);
        workOrderMapper.insert(add);
        return add.getId();
    }

    /**
     * 从生产计划生成工单
     */
    @Transactional
    public Long generateFromPlan(Long planId, Long productId, BigDecimal plannedQuantity,
                                  Long equipmentId, Long moldId, String operator) {
        Assert.notNull(planId, "生产计划ID不能为空");
        Assert.notNull(productId, "物料ID不能为空");
        Assert.notNull(plannedQuantity, "计划数量不能为空");
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderNo(generateWorkOrderNo());
        workOrder.setPlanId(planId);
        workOrder.setProductId(productId);
        workOrder.setPlannedQuantity(plannedQuantity);
        workOrder.setCompletedQuantity(BigDecimal.ZERO);
        workOrder.setDefectiveQuantity(BigDecimal.ZERO);
        workOrder.setEquipmentId(equipmentId);
        workOrder.setMoldId(moldId);
        workOrder.setWorkStatus(WorkStatus.PENDING);
        workOrder.setOperator(operator);
        workOrderMapper.insert(workOrder);
        return workOrder.getId();
    }

    /**
     * 生成工单号
     */
    private String generateWorkOrderNo() {
        return "WO" + System.currentTimeMillis();
    }

    /**
     * 修改工单
     */
    @Transactional
    public void updateByBo(WorkOrderBo bo) {
        WorkOrder update = MapstructUtils.convert(bo, WorkOrder.class);
        workOrderMapper.updateById(update);
    }

    /**
     * 分配机台和模具
     */
    @Transactional
    public void assignEquipment(Long id, Long equipmentId, Long moldId) {
        WorkOrderVo vo = queryById(id);
        Assert.notNull(vo, "工单不存在");
        if (!WorkStatus.PENDING.equals(vo.getWorkStatus())) {
            throw new ServiceException("只有待生产状态的工单可以分配机台/模具");
        }
        LambdaUpdateWrapper<WorkOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WorkOrder::getId, id);
        wrapper.set(WorkOrder::getEquipmentId, equipmentId);
        wrapper.set(WorkOrder::getMoldId, moldId);
        workOrderMapper.update(null, wrapper);
    }

    /**
     * 状态流转：待生产 → 生产中（开工）
     */
    @Transactional
    public void startWork(Long id) {
        WorkOrderVo vo = queryById(id);
        Assert.notNull(vo, "工单不存在");
        if (!WorkStatus.PENDING.equals(vo.getWorkStatus()) && !WorkStatus.PAUSED.equals(vo.getWorkStatus())) {
            throw new ServiceException("只有待生产或暂停状态的工单可以开工");
        }
        LambdaUpdateWrapper<WorkOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WorkOrder::getId, id);
        wrapper.set(WorkOrder::getWorkStatus, WorkStatus.IN_PROGRESS);
        if (vo.getActualStartTime() == null) {
            wrapper.set(WorkOrder::getActualStartTime, LocalDateTime.now());
        }
        workOrderMapper.update(null, wrapper);
    }

    /**
     * 状态流转：生产中 → 暂停
     */
    @Transactional
    public void pause(Long id) {
        WorkOrderVo vo = queryById(id);
        Assert.notNull(vo, "工单不存在");
        if (!WorkStatus.IN_PROGRESS.equals(vo.getWorkStatus())) {
            throw new ServiceException("只有生产中的工单可以暂停");
        }
        LambdaUpdateWrapper<WorkOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WorkOrder::getId, id);
        wrapper.set(WorkOrder::getWorkStatus, WorkStatus.PAUSED);
        workOrderMapper.update(null, wrapper);
    }

    /**
     * 状态流转：暂停 → 生产中（恢复）
     */
    @Transactional
    public void resume(Long id) {
        WorkOrderVo vo = queryById(id);
        Assert.notNull(vo, "工单不存在");
        if (!WorkStatus.PAUSED.equals(vo.getWorkStatus())) {
            throw new ServiceException("只有暂停状态的工单可以恢复");
        }
        LambdaUpdateWrapper<WorkOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WorkOrder::getId, id);
        wrapper.set(WorkOrder::getWorkStatus, WorkStatus.IN_PROGRESS);
        workOrderMapper.update(null, wrapper);
    }

    /**
     * 状态流转：生产中/暂停 → 已完成
     */
    @Transactional
    public void complete(Long id) {
        WorkOrderVo vo = queryById(id);
        Assert.notNull(vo, "工单不存在");
        if (!WorkStatus.IN_PROGRESS.equals(vo.getWorkStatus()) && !WorkStatus.PAUSED.equals(vo.getWorkStatus())) {
            throw new ServiceException("只有生产中或暂停状态的工单可以完工");
        }
        LambdaUpdateWrapper<WorkOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WorkOrder::getId, id);
        wrapper.set(WorkOrder::getWorkStatus, WorkStatus.COMPLETED);
        wrapper.set(WorkOrder::getActualEndTime, LocalDateTime.now());
        workOrderMapper.update(null, wrapper);
    }

    /**
     * 状态流转：任意非终态 → 已取消
     */
    @Transactional
    public void cancel(Long id) {
        WorkOrderVo vo = queryById(id);
        Assert.notNull(vo, "工单不存在");
        if (WorkStatus.COMPLETED.equals(vo.getWorkStatus()) || WorkStatus.CANCELLED.equals(vo.getWorkStatus())) {
            throw new ServiceException("已完成或已取消的工单无法再次操作");
        }
        LambdaUpdateWrapper<WorkOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(WorkOrder::getId, id);
        wrapper.set(WorkOrder::getWorkStatus, WorkStatus.CANCELLED);
        workOrderMapper.update(null, wrapper);
    }

    /**
     * 删除工单
     */
    public void deleteById(Long id) {
        validateBeforeDelete(id);
        workOrderMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(this::deleteById);
    }

    private void validateBeforeDelete(Long id) {
        WorkOrderVo vo = queryById(id);
        Assert.notNull(vo, "工单不存在");
        Integer status = vo.getWorkStatus();
        if (WorkStatus.IN_PROGRESS.equals(status)) {
            throw new ServiceException("删除失败", null, "工单【" + vo.getWorkOrderNo() + "】正在生产中，无法删除！");
        }
        if (WorkStatus.COMPLETED.equals(status)) {
            throw new ServiceException("删除失败", null, "工单【" + vo.getWorkOrderNo() + "】已完成，无法删除！");
        }
    }

    private void validateWorkOrderNo(String workOrderNo) {
        LambdaQueryWrapper<WorkOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(WorkOrder::getWorkOrderNo, workOrderNo);
        WorkOrder exist = workOrderMapper.selectOne(lqw);
        Assert.isNull(exist, "工单号重复，请重新输入");
    }

}
