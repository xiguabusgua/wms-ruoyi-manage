package com.ruoyi.wms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.OeeRecordBo;
import com.ruoyi.wms.domain.entity.OeeRecord;
import com.ruoyi.wms.domain.vo.OeeRecordVo;
import com.ruoyi.wms.mapper.OeeRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * OEE记录Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class OeeRecordService {

    private final OeeRecordMapper oeeRecordMapper;

    /**
     * 查询OEE记录
     */
    public OeeRecordVo queryById(Long id) {
        return oeeRecordMapper.selectVoById(id);
    }

    /**
     * 查询OEE记录列表
     */
    public TableDataInfo<OeeRecordVo> queryPageList(OeeRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<OeeRecord> lqw = buildQueryWrapper(bo);
        Page<OeeRecordVo> result = oeeRecordMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询OEE记录列表
     */
    public List<OeeRecordVo> queryList(OeeRecordBo bo) {
        LambdaQueryWrapper<OeeRecord> lqw = buildQueryWrapper(bo);
        return oeeRecordMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<OeeRecord> buildQueryWrapper(OeeRecordBo bo) {
        LambdaQueryWrapper<OeeRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEquipmentId() != null, OeeRecord::getEquipmentId, bo.getEquipmentId());
        lqw.ge(bo.getRecordDate() != null, OeeRecord::getRecordDate, bo.getRecordDate());
        lqw.orderByDesc(OeeRecord::getRecordDate);
        return lqw;
    }

    /**
     * 新增OEE记录，自动计算OEE指标
     */
    public void insertByBo(OeeRecordBo bo) {
        OeeRecord add = MapstructUtils.convert(bo, OeeRecord.class);
        calculateOeeMetrics(add);
        oeeRecordMapper.insert(add);
    }

    /**
     * 修改OEE记录
     */
    public void updateByBo(OeeRecordBo bo) {
        OeeRecord update = MapstructUtils.convert(bo, OeeRecord.class);
        calculateOeeMetrics(update);
        oeeRecordMapper.updateById(update);
    }

    /**
     * 计算OEE各项指标：可用率、性能率、品质率、综合OEE
     */
    private void calculateOeeMetrics(OeeRecord record) {
        Integer plannedTime = record.getPlannedTime();
        Integer runTime = record.getRunTime();
        Integer downtime = record.getDowntime();
        Integer goodCount = record.getGoodCount();
        Integer totalCount = record.getTotalCount();

        if (plannedTime != null && plannedTime > 0) {
            int actualRunTime = runTime != null ? runTime : 0;
            int actualDowntime = downtime != null ? downtime : 0;
            int availableTime = plannedTime - actualDowntime;
            if (availableTime > 0) {
                BigDecimal availabilityRate = new BigDecimal(actualRunTime)
                    .multiply(new BigDecimal("100"))
                    .divide(new BigDecimal(availableTime), 4, RoundingMode.HALF_UP);
                record.setAvailabilityRate(availabilityRate);
            } else {
                record.setAvailabilityRate(BigDecimal.ZERO);
            }
        }

        if (runTime != null && runTime > 0 && totalCount != null && totalCount > 0) {
            BigDecimal performanceRate = new BigDecimal(totalCount)
                .multiply(new BigDecimal("100"))
                .divide(new BigDecimal(runTime), 4, RoundingMode.HALF_UP);
            record.setPerformanceRate(performanceRate);
        }

        if (totalCount != null && totalCount > 0) {
            int actualGoodCount = goodCount != null ? goodCount : 0;
            BigDecimal qualityRate = new BigDecimal(actualGoodCount)
                .multiply(new BigDecimal("100"))
                .divide(new BigDecimal(totalCount), 4, RoundingMode.HALF_UP);
            record.setQualityRate(qualityRate);
        }

        BigDecimal ar = record.getAvailabilityRate() != null ? record.getAvailabilityRate() : BigDecimal.ZERO;
        BigDecimal pr = record.getPerformanceRate() != null ? record.getPerformanceRate() : BigDecimal.ZERO;
        BigDecimal qr = record.getQualityRate() != null ? record.getQualityRate() : BigDecimal.ZERO;
        BigDecimal oee = ar.multiply(pr).multiply(qr)
            .divide(new BigDecimal("10000"), 4, RoundingMode.HALF_UP);
        record.setOee(oee);
    }

    /**
     * 统计设备在指定日期范围内的平均OEE
     */
    public BigDecimal avgOeeByDateRange(Long equipmentId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<OeeRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(OeeRecord::getEquipmentId, equipmentId);
        lqw.between(OeeRecord::getRecordDate, startDate, endDate);
        lqw.select(OeeRecord::getOee);
        List<OeeRecord> list = oeeRecordMapper.selectList(lqw);
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return list.stream()
            .map(r -> r.getOee() != null ? r.getOee() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(new BigDecimal(list.size()), 4, RoundingMode.HALF_UP);
    }

    /**
     * 删除OEE记录
     */
    public void deleteByIds(Collection<Long> ids) {
        oeeRecordMapper.deleteBatchIds(ids);
    }

}
