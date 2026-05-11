package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.QuotationOrderDetailBo;
import com.ruoyi.wms.domain.entity.QuotationOrderDetail;
import com.ruoyi.wms.domain.vo.QuotationOrderDetailVo;
import com.ruoyi.wms.mapper.QuotationOrderDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 报价单明细Service业务层处理
 *
 * @author zcc
 */
@RequiredArgsConstructor
@Service
public class QuotationOrderDetailService extends ServiceImpl<QuotationOrderDetailMapper, QuotationOrderDetail> {

    private final QuotationOrderDetailMapper quotationOrderDetailMapper;
    private final ItemSkuService itemSkuService;

    /**
     * 查询报价单明细
     */
    public QuotationOrderDetailVo queryById(Long id) {
        return quotationOrderDetailMapper.selectVoById(id);
    }

    public TableDataInfo<QuotationOrderDetailVo> queryPageList(QuotationOrderDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<QuotationOrderDetail> lqw = buildQueryWrapper(bo);
        Page<QuotationOrderDetailVo> result = quotationOrderDetailMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<QuotationOrderDetailVo> queryList(QuotationOrderDetailBo bo) {
        LambdaQueryWrapper<QuotationOrderDetail> lqw = buildQueryWrapper(bo);
        return quotationOrderDetailMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<QuotationOrderDetail> buildQueryWrapper(QuotationOrderDetailBo bo) {
        LambdaQueryWrapper<QuotationOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOrderId() != null, QuotationOrderDetail::getOrderId, bo.getOrderId());
        lqw.eq(bo.getSkuId() != null, QuotationOrderDetail::getSkuId, bo.getSkuId());
        lqw.eq(bo.getQuantity() != null, QuotationOrderDetail::getQuantity, bo.getQuantity());
        lqw.eq(bo.getUnitPrice() != null, QuotationOrderDetail::getUnitPrice, bo.getUnitPrice());
        return lqw;
    }

    public void insertByBo(QuotationOrderDetailBo bo) {
        QuotationOrderDetail add = MapstructUtils.convert(bo, QuotationOrderDetail.class);
        quotationOrderDetailMapper.insert(add);
    }

    public void updateByBo(QuotationOrderDetailBo bo) {
        QuotationOrderDetail update = MapstructUtils.convert(bo, QuotationOrderDetail.class);
        quotationOrderDetailMapper.updateById(update);
    }

    public void deleteByIds(Collection<Long> ids) {
        quotationOrderDetailMapper.deleteBatchIds(ids);
    }

    @Transactional
    public void saveDetails(List<QuotationOrderDetail> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    public List<QuotationOrderDetailVo> queryByQuotationOrderId(Long quotationOrderId) {
        QuotationOrderDetailBo bo = new QuotationOrderDetailBo();
        bo.setOrderId(quotationOrderId);
        List<QuotationOrderDetailVo> details = queryList(bo);
        if (CollUtil.isEmpty(details)) {
            return Collections.emptyList();
        }
        itemSkuService.setItemSkuMap(details);
        return details;
    }

    /**
     * 根据报价单id删除明细
     */
    public void deleteByQuotationOrderId(@jakarta.validation.constraints.NotNull Long quotationOrderId) {
        LambdaQueryWrapper<QuotationOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(QuotationOrderDetail::getOrderId, quotationOrderId);
        quotationOrderDetailMapper.delete(lqw);
    }

}
