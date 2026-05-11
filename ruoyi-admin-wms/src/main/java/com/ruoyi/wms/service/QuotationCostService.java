package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.QuotationCostBo;
import com.ruoyi.wms.domain.entity.QuotationCost;
import com.ruoyi.wms.domain.vo.QuotationCostVo;
import com.ruoyi.wms.mapper.QuotationCostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 成本核算明细Service业务层处理
 *
 * @author zcc
 */
@RequiredArgsConstructor
@Service
public class QuotationCostService extends ServiceImpl<QuotationCostMapper, QuotationCost> {

    private final QuotationCostMapper quotationCostMapper;
    private final ItemSkuService itemSkuService;

    public QuotationCostVo queryById(Long id) {
        return quotationCostMapper.selectVoById(id);
    }

    public TableDataInfo<QuotationCostVo> queryPageList(QuotationCostBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<QuotationCost> lqw = buildQueryWrapper(bo);
        Page<QuotationCostVo> result = quotationCostMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<QuotationCostVo> queryList(QuotationCostBo bo) {
        LambdaQueryWrapper<QuotationCost> lqw = buildQueryWrapper(bo);
        List<QuotationCostVo> vos = quotationCostMapper.selectVoList(lqw);
        if (CollUtil.isNotEmpty(vos)) {
            setItemSkuMapForCost(vos);
        }
        return vos;
    }

    private LambdaQueryWrapper<QuotationCost> buildQueryWrapper(QuotationCostBo bo) {
        LambdaQueryWrapper<QuotationCost> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getQuotationOrderId() != null, QuotationCost::getQuotationOrderId, bo.getQuotationOrderId());
        lqw.eq(bo.getSkuId() != null, QuotationCost::getSkuId, bo.getSkuId());
        lqw.eq(StringUtils.isNotBlank(bo.getBomVersion()), QuotationCost::getBomVersion, bo.getBomVersion());
        return lqw;
    }

    public void insertByBo(QuotationCostBo bo) {
        QuotationCost add = MapstructUtils.convert(bo, QuotationCost.class);
        quotationCostMapper.insert(add);
    }

    public void updateByBo(QuotationCostBo bo) {
        QuotationCost update = MapstructUtils.convert(bo, QuotationCost.class);
        quotationCostMapper.updateById(update);
    }

    public void deleteByIds(Collection<Long> ids) {
        quotationCostMapper.deleteBatchIds(ids);
    }

    @Transactional
    public void saveCosts(List<QuotationCost> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    public List<QuotationCostVo> queryByQuotationOrderId(Long quotationOrderId) {
        QuotationCostBo bo = new QuotationCostBo();
        bo.setQuotationOrderId(quotationOrderId);
        List<QuotationCostVo> costs = queryList(bo);
        if (CollUtil.isEmpty(costs)) {
            return CollUtil.newArrayList();
        }
        return costs;
    }

    /**
     * 根据报价单id删除成本核算明细
     */
    public void deleteByQuotationOrderId(@jakarta.validation.constraints.NotNull Long quotationOrderId) {
        LambdaQueryWrapper<QuotationCost> lqw = Wrappers.lambdaQuery();
        lqw.eq(QuotationCost::getQuotationOrderId, quotationOrderId);
        quotationCostMapper.delete(lqw);
    }

    /**
     * 为成本核算VO填充SKU和物料信息
     */
    public void setItemSkuMapForCost(List<QuotationCostVo> costs) {
        if (CollUtil.isEmpty(costs)) {
            return;
        }
        java.util.Set<Long> skuIds = costs.stream()
            .map(QuotationCostVo::getSkuId)
            .collect(java.util.stream.Collectors.toSet());

        Map<Long, ItemSkuMapVo> itemSkuMap = itemSkuService.queryItemSkuMapVosByIds(skuIds);

        costs.forEach(cost -> {
            ItemSkuMapVo vo = itemSkuMap.get(cost.getSkuId());
            if (vo != null) {
                cost.setItemSku(vo.getItemSku());
                cost.setItem(vo.getItem());
            }
        });
    }

    /**
     * 计算产品总成本（材料+人工+制造+其他）
     */
    public BigDecimal calculateTotalCost(BigDecimal materialCost, BigDecimal laborCost,
                                         BigDecimal manufacturingCost, BigDecimal otherCost) {
        BigDecimal total = BigDecimal.ZERO;
        total = addIfNotNull(total, materialCost);
        total = addIfNotNull(total, laborCost);
        total = addIfNotNull(total, manufacturingCost);
        total = addIfNotNull(total, otherCost);
        return total;
    }

    private BigDecimal addIfNotNull(BigDecimal base, BigDecimal value) {
        if (value != null) {
            return base.add(value);
        }
        return base;
    }

}
