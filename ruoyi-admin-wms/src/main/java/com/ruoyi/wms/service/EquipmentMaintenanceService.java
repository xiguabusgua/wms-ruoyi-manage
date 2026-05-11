package com.ruoyi.wms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.EquipmentMaintenanceBo;
import com.ruoyi.wms.domain.entity.EquipmentMaintenance;
import com.ruoyi.wms.domain.vo.EquipmentMaintenanceVo;
import com.ruoyi.wms.mapper.EquipmentMaintenanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * 设备维护记录Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class EquipmentMaintenanceService {

    private final EquipmentMaintenanceMapper equipmentMaintenanceMapper;

    /**
     * 查询设备维护记录
     */
    public EquipmentMaintenanceVo queryById(Long id) {
        return equipmentMaintenanceMapper.selectVoById(id);
    }

    /**
     * 查询设备维护记录列表
     */
    public TableDataInfo<EquipmentMaintenanceVo> queryPageList(EquipmentMaintenanceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<EquipmentMaintenance> lqw = buildQueryWrapper(bo);
        Page<EquipmentMaintenanceVo> result = equipmentMaintenanceMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备维护记录列表
     */
    public List<EquipmentMaintenanceVo> queryList(EquipmentMaintenanceBo bo) {
        LambdaQueryWrapper<EquipmentMaintenance> lqw = buildQueryWrapper(bo);
        return equipmentMaintenanceMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<EquipmentMaintenance> buildQueryWrapper(EquipmentMaintenanceBo bo) {
        LambdaQueryWrapper<EquipmentMaintenance> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEquipmentId() != null, EquipmentMaintenance::getEquipmentId, bo.getEquipmentId());
        lqw.eq(bo.getMaintenanceType() != null, EquipmentMaintenance::getMaintenanceType, bo.getMaintenanceType());
        lqw.eq(bo.getStatus() != null, EquipmentMaintenance::getStatus, bo.getStatus());
        lqw.ge(bo.getMaintenanceDate() != null, EquipmentMaintenance::getMaintenanceDate, bo.getMaintenanceDate());
        lqw.le(bo.getNextDate() != null, EquipmentMaintenance::getNextDate, bo.getNextDate());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增设备维护记录
     */
    public void insertByBo(EquipmentMaintenanceBo bo) {
        EquipmentMaintenance add = MapstructUtils.convert(bo, EquipmentMaintenance.class);
        if (add.getStatus() == null) {
            add.setStatus(0);
        }
        equipmentMaintenanceMapper.insert(add);
    }

    /**
     * 修改设备维护记录
     */
    public void updateByBo(EquipmentMaintenanceBo bo) {
        EquipmentMaintenance update = MapstructUtils.convert(bo, EquipmentMaintenance.class);
        equipmentMaintenanceMapper.updateById(update);
    }

    /**
     * 完成维护任务
     */
    public void completeMaintenance(Long id, BigDecimal actualCost) {
        EquipmentMaintenance maintenance = new EquipmentMaintenance();
        maintenance.setId(id);
        maintenance.setStatus(2);
        if (actualCost != null) {
            maintenance.setCost(actualCost);
        }
        equipmentMaintenanceMapper.updateById(maintenance);
    }

    /**
     * 统计指定时间范围内设备的维护费用
     */
    public BigDecimal sumCostByDateRange(Long equipmentId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<EquipmentMaintenance> lqw = Wrappers.lambdaQuery();
        lqw.eq(EquipmentMaintenance::getEquipmentId, equipmentId);
        lqw.between(EquipmentMaintenance::getMaintenanceDate, startDate, endDate);
        lqw.isNotNull(EquipmentMaintenance::getCost);
        lqw.select(EquipmentMaintenance::getCost);
        List<EquipmentMaintenance> list = equipmentMaintenanceMapper.selectList(lqw);
        return list.stream()
            .map(m -> m.getCost() != null ? m.getCost() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 删除设备维护记录
     */
    public void deleteByIds(Collection<Long> ids) {
        equipmentMaintenanceMapper.deleteBatchIds(ids);
    }

}
