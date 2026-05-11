package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.PurchaseOrderDetailBo;
import com.ruoyi.wms.domain.entity.PurchaseOrderDetail;
import com.ruoyi.wms.domain.vo.PurchaseOrderDetailVo;
import com.ruoyi.wms.mapper.PurchaseOrderDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 采购订单明细Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class PurchaseOrderDetailService extends ServiceImpl<PurchaseOrderDetailMapper, PurchaseOrderDetail> {

    private final PurchaseOrderDetailMapper purchaseOrderDetailMapper;

    /**
     * 查询采购订单明细
     */
    public PurchaseOrderDetailVo queryById(Long id) {
        return purchaseOrderDetailMapper.selectVoById(id);
    }

    public TableDataInfo<PurchaseOrderDetailVo> queryPageList(PurchaseOrderDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PurchaseOrderDetail> lqw = buildQueryWrapper(bo);
        Page<PurchaseOrderDetailVo> result = purchaseOrderDetailMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<PurchaseOrderDetailVo> queryList(PurchaseOrderDetailBo bo) {
        LambdaQueryWrapper<PurchaseOrderDetail> lqw = buildQueryWrapper(bo);
        return purchaseOrderDetailMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PurchaseOrderDetail> buildQueryWrapper(PurchaseOrderDetailBo bo) {
        LambdaQueryWrapper<PurchaseOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOrderId() != null, PurchaseOrderDetail::getOrderId, bo.getOrderId());
        lqw.eq(bo.getItemId() != null, PurchaseOrderDetail::getItemId, bo.getItemId());
        lqw.eq(bo.getSkuId() != null, PurchaseOrderDetail::getSkuId, bo.getSkuId());
        return lqw;
    }

    public void insertByBo(PurchaseOrderDetailBo bo) {
        PurchaseOrderDetail add = MapstructUtils.convert(bo, PurchaseOrderDetail.class);
        purchaseOrderDetailMapper.insert(add);
    }

    public void updateByBo(PurchaseOrderDetailBo bo) {
        PurchaseOrderDetail update = MapstructUtils.convert(bo, PurchaseOrderDetail.class);
        purchaseOrderDetailMapper.updateById(update);
    }

    public void deleteByIds(Collection<Long> ids) {
        purchaseOrderDetailMapper.deleteBatchIds(ids);
    }

    @Transactional
    public void saveDetails(List<PurchaseOrderDetail> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    public List<PurchaseOrderDetailVo> queryByOrderId(Long orderId) {
        PurchaseOrderDetailBo bo = new PurchaseOrderDetailBo();
        bo.setOrderId(orderId);
        List<PurchaseOrderDetailVo> details = queryList(bo);
        if (CollUtil.isEmpty(details)) {
            return Collections.emptyList();
        }
        return details;
    }

    /**
     * 根据采购订单ID删除明细
     */
    public void deleteByOrderId(@jakarta.validation.constraints.NotNull Long orderId) {
        LambdaQueryWrapper<PurchaseOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseOrderDetail::getOrderId, orderId);
        purchaseOrderDetailMapper.delete(lqw);
    }

}
