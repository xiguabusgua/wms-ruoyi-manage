package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.BomDetailBo;
import com.ruoyi.wms.domain.entity.BomDetail;
import com.ruoyi.wms.domain.vo.BomDetailVo;
import com.ruoyi.wms.mapper.BomDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * BOM明细Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class BomDetailService extends ServiceImpl<BomDetailMapper, BomDetail> {

    private final BomDetailMapper bomDetailMapper;

    /**
     * 查询BOM明细
     */
    public BomDetailVo queryById(Long id) {
        return bomDetailMapper.selectVoById(id);
    }

    public TableDataInfo<BomDetailVo> queryPageList(BomDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BomDetail> lqw = buildQueryWrapper(bo);
        Page<BomDetailVo> result = bomDetailMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<BomDetailVo> queryList(BomDetailBo bo) {
        LambdaQueryWrapper<BomDetail> lqw = buildQueryWrapper(bo);
        return bomDetailMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BomDetail> buildQueryWrapper(BomDetailBo bo) {
        LambdaQueryWrapper<BomDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getBomId() != null, BomDetail::getBomId, bo.getBomId());
        lqw.eq(bo.getItemId() != null, BomDetail::getItemId, bo.getItemId());
        lqw.eq(bo.getSkuId() != null, BomDetail::getSkuId, bo.getSkuId());
        return lqw;
    }

    public void insertByBo(BomDetailBo bo) {
        BomDetail add = MapstructUtils.convert(bo, BomDetail.class);
        bomDetailMapper.insert(add);
    }

    public void updateByBo(BomDetailBo bo) {
        BomDetail update = MapstructUtils.convert(bo, BomDetail.class);
        bomDetailMapper.updateById(update);
    }

    public void deleteByIds(Collection<Long> ids) {
        bomDetailMapper.deleteBatchIds(ids);
    }

    @Transactional
    public void saveDetails(List<BomDetail> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    public List<BomDetailVo> queryByBomId(Long bomId) {
        BomDetailBo bo = new BomDetailBo();
        bo.setBomId(bomId);
        List<BomDetailVo> details = queryList(bo);
        if (CollUtil.isEmpty(details)) {
            return Collections.emptyList();
        }
        return details;
    }

    /**
     * 根据BOM主表ID删除明细
     */
    public void deleteByBomId(@jakarta.validation.constraints.NotNull Long bomId) {
        LambdaQueryWrapper<BomDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(BomDetail::getBomId, bomId);
        bomDetailMapper.delete(lqw);
    }

}
