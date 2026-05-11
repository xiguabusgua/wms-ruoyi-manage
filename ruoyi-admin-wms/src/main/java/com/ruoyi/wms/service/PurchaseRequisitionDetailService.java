package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.PurchaseRequisitionDetailBo;
import com.ruoyi.wms.domain.entity.PurchaseRequisitionDetail;
import com.ruoyi.wms.domain.vo.PurchaseRequisitionDetailVo;
import com.ruoyi.wms.mapper.PurchaseRequisitionDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 采购申请明细Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class PurchaseRequisitionDetailService extends ServiceImpl<PurchaseRequisitionDetailMapper, PurchaseRequisitionDetail> {

    private final PurchaseRequisitionDetailMapper purchaseRequisitionDetailMapper;

    /**
     * 查询采购申请明细
     */
    public PurchaseRequisitionDetailVo queryById(Long id) {
        return purchaseRequisitionDetailMapper.selectVoById(id);
    }

    public TableDataInfo<PurchaseRequisitionDetailVo> queryPageList(PurchaseRequisitionDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PurchaseRequisitionDetail> lqw = buildQueryWrapper(bo);
        Page<PurchaseRequisitionDetailVo> result = purchaseRequisitionDetailMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<PurchaseRequisitionDetailVo> queryList(PurchaseRequisitionDetailBo bo) {
        LambdaQueryWrapper<PurchaseRequisitionDetail> lqw = buildQueryWrapper(bo);
        return purchaseRequisitionDetailMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PurchaseRequisitionDetail> buildQueryWrapper(PurchaseRequisitionDetailBo bo) {
        LambdaQueryWrapper<PurchaseRequisitionDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRequisitionId() != null, PurchaseRequisitionDetail::getRequisitionId, bo.getRequisitionId());
        lqw.eq(bo.getItemId() != null, PurchaseRequisitionDetail::getItemId, bo.getItemId());
        lqw.eq(bo.getSkuId() != null, PurchaseRequisitionDetail::getSkuId, bo.getSkuId());
        return lqw;
    }

    public void insertByBo(PurchaseRequisitionDetailBo bo) {
        PurchaseRequisitionDetail add = MapstructUtils.convert(bo, PurchaseRequisitionDetail.class);
        purchaseRequisitionDetailMapper.insert(add);
    }

    public void updateByBo(PurchaseRequisitionDetailBo bo) {
        PurchaseRequisitionDetail update = MapstructUtils.convert(bo, PurchaseRequisitionDetail.class);
        purchaseRequisitionDetailMapper.updateById(update);
    }

    public void deleteByIds(Collection<Long> ids) {
        purchaseRequisitionDetailMapper.deleteBatchIds(ids);
    }

    @Transactional
    public void saveDetails(List<PurchaseRequisitionDetail> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    public List<PurchaseRequisitionDetailVo> queryByRequisitionId(Long requisitionId) {
        PurchaseRequisitionDetailBo bo = new PurchaseRequisitionDetailBo();
        bo.setRequisitionId(requisitionId);
        List<PurchaseRequisitionDetailVo> details = queryList(bo);
        if (CollUtil.isEmpty(details)) {
            return Collections.emptyList();
        }
        return details;
    }

    /**
     * 根据采购申请ID删除明细
     */
    public void deleteByRequisitionId(@jakarta.validation.constraints.NotNull Long requisitionId) {
        LambdaQueryWrapper<PurchaseRequisitionDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseRequisitionDetail::getRequisitionId, requisitionId);
        purchaseRequisitionDetailMapper.delete(lqw);
    }

}
