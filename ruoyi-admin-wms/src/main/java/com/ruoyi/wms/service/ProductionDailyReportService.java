package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.ProductionDailyReportBo;
import com.ruoyi.wms.domain.entity.ProductionDailyReport;
import com.ruoyi.wms.domain.vo.ProductionDailyReportVo;
import com.ruoyi.wms.mapper.ProductionDailyReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;

/**
 * 生产日报Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class ProductionDailyReportService {

    private final ProductionDailyReportMapper productionDailyReportMapper;

    /**
     * 查询生产日报
     */
    public ProductionDailyReportVo queryById(Long id) {
        return productionDailyReportMapper.selectVoById(id);
    }

    /**
     * 查询生产日报列表
     */
    public TableDataInfo<ProductionDailyReportVo> queryPageList(ProductionDailyReportBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ProductionDailyReport> lqw = buildQueryWrapper(bo);
        Page<ProductionDailyReportVo> result = productionDailyReportMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询生产日报列表
     */
    public java.util.List<ProductionDailyReportVo> queryList(ProductionDailyReportBo bo) {
        LambdaQueryWrapper<ProductionDailyReport> lqw = buildQueryWrapper(bo);
        return productionDailyReportMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ProductionDailyReport> buildQueryWrapper(ProductionDailyReportBo bo) {
        LambdaQueryWrapper<ProductionDailyReport> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getReportDate() != null, ProductionDailyReport::getReportDate, bo.getReportDate());
        lqw.eq(bo.getWorkOrderId() != null, ProductionDailyReport::getWorkOrderId, bo.getWorkOrderId());
        lqw.eq(bo.getProductId() != null, ProductionDailyReport::getProductId, bo.getProductId());
        lqw.like(StringUtils.isNotBlank(bo.getOperator()), ProductionDailyReport::getOperator, bo.getOperator());
        lqw.ge(bo.getReportDate() != null && bo.getParams().get("beginReportDate") != null,
            ProductionDailyReport::getReportDate, bo.getParams().get("beginReportDate"));
        lqw.le(bo.getReportDate() != null && bo.getParams().get("endReportDate") != null,
            ProductionDailyReport::getReportDate, bo.getParams().get("endReportDate"));
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增生产日报 - 自动计算良率
     */
    @Transactional
    public Long insertByBo(ProductionDailyReportBo bo) {
        calculateYieldRate(bo);
        ProductionDailyReport add = MapstructUtils.convert(bo, ProductionDailyReport.class);
        productionDailyReportMapper.insert(add);
        return add.getId();
    }

    /**
     * 修改生产日报 - 自动重算良率
     */
    @Transactional
    public void updateByBo(ProductionDailyReportBo bo) {
        calculateYieldRate(bo);
        ProductionDailyReport update = MapstructUtils.convert(bo, ProductionDailyReport.class);
        productionDailyReportMapper.updateById(update);
    }

    /**
     * 计算良率：良率 = (完工数量 / (完工数量 + 不良数量)) * 100
     */
    private void calculateYieldRate(ProductionDailyReportBo bo) {
        BigDecimal completedQty = bo.getCompletedQuantity() != null ? bo.getCompletedQuantity() : BigDecimal.ZERO;
        BigDecimal defectiveQty = bo.getDefectiveQuantity() != null ? bo.getDefectiveQuantity() : BigDecimal.ZERO;
        BigDecimal total = completedQty.add(defectiveQty);
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal yieldRate = completedQty.divide(total, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
            bo.setYieldRate(yieldRate);
        } else {
            bo.setYieldRate(BigDecimal.ZERO);
        }
    }

    /**
     * 删除生产日报
     */
    public void deleteById(Long id) {
        productionDailyReportMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        productionDailyReportMapper.deleteByIds(ids);
    }

}
