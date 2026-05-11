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
import com.ruoyi.wms.domain.bo.IqcInspectionBo;
import com.ruoyi.wms.domain.entity.IqcInspection;
import com.ruoyi.wms.domain.vo.IqcInspectionVo;
import com.ruoyi.wms.mapper.IqcInspectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IqcInspectionService {

    private final IqcInspectionMapper iqcInspectionMapper;

    public IqcInspectionVo queryById(Long id) {
        IqcInspectionVo vo = iqcInspectionMapper.selectVoById(id);
        Assert.notNull(vo, "来料检验记录不存在");
        return vo;
    }

    public TableDataInfo<IqcInspectionVo> queryPageList(IqcInspectionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IqcInspection> lqw = buildQueryWrapper(bo);
        Page<IqcInspectionVo> result = iqcInspectionMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<IqcInspectionVo> queryList(IqcInspectionBo bo) {
        LambdaQueryWrapper<IqcInspection> lqw = buildQueryWrapper(bo);
        return iqcInspectionMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IqcInspection> buildQueryWrapper(IqcInspectionBo bo) {
        LambdaQueryWrapper<IqcInspection> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTaskId() != null, IqcInspection::getTaskId, bo.getTaskId());
        lqw.eq(bo.getReceiptOrderId() != null, IqcInspection::getReceiptOrderId, bo.getReceiptOrderId());
        lqw.eq(bo.getSupplierId() != null, IqcInspection::getSupplierId, bo.getSupplierId());
        lqw.like(StringUtils.isNotBlank(bo.getMaterialName()), IqcInspection::getMaterialName, bo.getMaterialName());
        lqw.eq(StringUtils.isNotBlank(bo.getBatchNo()), IqcInspection::getBatchNo, bo.getBatchNo());
        lqw.eq(bo.getConclusion() != null, IqcInspection::getConclusion, bo.getConclusion());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    public Long insertByBo(IqcInspectionBo bo) {
        IqcInspection add = MapstructUtils.convert(bo, IqcInspection.class);
        iqcInspectionMapper.insert(add);
        return add.getId();
    }

    public void updateByBo(IqcInspectionBo bo) {
        IqcInspection update = MapstructUtils.convert(bo, IqcInspection.class);
        iqcInspectionMapper.updateById(update);
    }

    public void deleteById(Long id) {
        iqcInspectionMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(id -> iqcInspectionMapper.deleteById(id));
    }

}
