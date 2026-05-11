package com.ruoyi.wms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.CostAnalysisBo;
import com.ruoyi.wms.domain.entity.CostAnalysis;
import com.ruoyi.wms.domain.vo.CostAnalysisVo;
import com.ruoyi.wms.mapper.CostAnalysisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 成本分析Service业务层处理
 *
 * @author zcc
 * @date 2024-08-15
 */
@RequiredArgsConstructor
@Service
public class CostAnalysisService {

    private final CostAnalysisMapper costAnalysisMapper;

    /**
     * 查询成本分析
     */
    public CostAnalysisVo queryById(Long id) {
        return costAnalysisMapper.selectVoById(id);
    }

    /**
     * 查询成本分析列表
     */
    public TableDataInfo<CostAnalysisVo> queryPageList(CostAnalysisBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CostAnalysis> lqw = buildQueryWrapper(bo);
        Page<CostAnalysisVo> result = costAnalysisMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询成本分析列表
     */
    public List<CostAnalysisVo> queryList(CostAnalysisBo bo) {
        LambdaQueryWrapper<CostAnalysis> lqw = buildQueryWrapper(bo);
        return costAnalysisMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CostAnalysis> buildQueryWrapper(CostAnalysisBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CostAnalysis> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getAnalysisNo()), CostAnalysis::getAnalysisNo, bo.getAnalysisNo());
        lqw.eq(bo.getProductId() != null, CostAnalysis::getProductId, bo.getProductId());
        lqw.eq(bo.getAnalysisDate() != null, CostAnalysis::getAnalysisDate, bo.getAnalysisDate());
        lqw.eq(bo.getAnalysisPeriod() != null, CostAnalysis::getAnalysisPeriod, bo.getAnalysisPeriod());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增成本分析
     */
    public void insertByBo(CostAnalysisBo bo) {
        CostAnalysis add = MapstructUtils.convert(bo, CostAnalysis.class);
        costAnalysisMapper.insert(add);
    }

    /**
     * 修改成本分析
     */
    public void updateByBo(CostAnalysisBo bo) {
        CostAnalysis update = MapstructUtils.convert(bo, CostAnalysis.class);
        costAnalysisMapper.updateById(update);
    }

    /**
     * 删除成本分析
     */
    public void deleteById(Long id) {
        costAnalysisMapper.deleteById(id);
    }

    /**
     * 批量删除成本分析
     */
    public void deleteByIds(Collection<Long> ids) {
        costAnalysisMapper.deleteBatchIds(ids);
    }

    /**
     * 计算单位成本
     *
     * @param materialCost      材料成本
     * @param laborCost         人工成本
     * @param manufacturingCost 制造费用
     * @param otherCost         其他费用
     * @param productionQuantity 产量
     * @return 单位成本
     */
    public BigDecimal calculateUnitCost(BigDecimal materialCost, BigDecimal laborCost,
                                         BigDecimal manufacturingCost, BigDecimal otherCost,
                                         Integer productionQuantity) {
        if (productionQuantity == null || productionQuantity <= 0) {
            throw new IllegalArgumentException("产量必须大于0");
        }
        BigDecimal totalCost = BigDecimal.ZERO;
        if (materialCost != null) {
            totalCost = totalCost.add(materialCost);
        }
        if (laborCost != null) {
            totalCost = totalCost.add(laborCost);
        }
        if (manufacturingCost != null) {
            totalCost = totalCost.add(manufacturingCost);
        }
        if (otherCost != null) {
            totalCost = totalCost.add(otherCost);
        }
        return totalCost.divide(BigDecimal.valueOf(productionQuantity), 4, BigDecimal.ROUND_HALF_UP);
    }
}
