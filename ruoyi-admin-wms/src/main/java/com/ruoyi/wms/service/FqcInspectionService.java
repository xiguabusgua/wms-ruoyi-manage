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
import com.ruoyi.wms.domain.bo.FqcInspectionBo;
import com.ruoyi.wms.domain.entity.FqcInspection;
import com.ruoyi.wms.domain.vo.FqcInspectionVo;
import com.ruoyi.wms.mapper.FqcInspectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FqcInspectionService {

    private final FqcInspectionMapper fqcInspectionMapper;

    public FqcInspectionVo queryById(Long id) {
        FqcInspectionVo vo = fqcInspectionMapper.selectVoById(id);
        Assert.notNull(vo, "成品检验记录不存在");
        return vo;
    }

    public TableDataInfo<FqcInspectionVo> queryPageList(FqcInspectionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FqcInspection> lqw = buildQueryWrapper(bo);
        Page<FqcInspectionVo> result = fqcInspectionMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<FqcInspectionVo> queryList(FqcInspectionBo bo) {
        LambdaQueryWrapper<FqcInspection> lqw = buildQueryWrapper(bo);
        return fqcInspectionMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FqcInspection> buildQueryWrapper(FqcInspectionBo bo) {
        LambdaQueryWrapper<FqcInspection> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTaskId() != null, FqcInspection::getTaskId, bo.getTaskId());
        lqw.eq(bo.getWorkOrderId() != null, FqcInspection::getWorkOrderId, bo.getWorkOrderId());
        lqw.eq(bo.getProductId() != null, FqcInspection::getProductId, bo.getProductId());
        lqw.eq(StringUtils.isNotBlank(bo.getBatchNo()), FqcInspection::getBatchNo, bo.getBatchNo());
        lqw.eq(StringUtils.isNotBlank(bo.getCustomerInspect()), FqcInspection::getCustomerInspect, bo.getCustomerInspect());
        lqw.eq(bo.getConclusion() != null, FqcInspection::getConclusion, bo.getConclusion());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    public Long insertByBo(FqcInspectionBo bo) {
        FqcInspection add = MapstructUtils.convert(bo, FqcInspection.class);
        fqcInspectionMapper.insert(add);
        return add.getId();
    }

    public void updateByBo(FqcInspectionBo bo) {
        FqcInspection update = MapstructUtils.convert(bo, FqcInspection.class);
        fqcInspectionMapper.updateById(update);
    }

    public void deleteById(Long id) {
        fqcInspectionMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(id -> fqcInspectionMapper.deleteById(id));
    }

}
