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
import com.ruoyi.wms.domain.bo.QualityTraceabilityBo;
import com.ruoyi.wms.domain.entity.QualityTraceability;
import com.ruoyi.wms.domain.vo.QualityTraceabilityVo;
import com.ruoyi.wms.mapper.QualityTraceabilityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QualityTraceabilityService {

    private final QualityTraceabilityMapper qualityTraceabilityMapper;

    public QualityTraceabilityVo queryById(Long id) {
        QualityTraceabilityVo vo = qualityTraceabilityMapper.selectVoById(id);
        Assert.notNull(vo, "质量追溯记录不存在");
        return vo;
    }

    public TableDataInfo<QualityTraceabilityVo> queryPageList(QualityTraceabilityBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<QualityTraceability> lqw = buildQueryWrapper(bo);
        Page<QualityTraceabilityVo> result = qualityTraceabilityMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<QualityTraceabilityVo> queryList(QualityTraceabilityBo bo) {
        LambdaQueryWrapper<QualityTraceability> lqw = buildQueryWrapper(bo);
        return qualityTraceabilityMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<QualityTraceability> buildQueryWrapper(QualityTraceabilityBo bo) {
        LambdaQueryWrapper<QualityTraceability> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTraceNo()), QualityTraceability::getTraceNo, bo.getTraceNo());
        lqw.eq(bo.getProductId() != null, QualityTraceability::getProductId, bo.getProductId());
        lqw.eq(StringUtils.isNotBlank(bo.getBatchNo()), QualityTraceability::getBatchNo, bo.getBatchNo());
        lqw.eq(StringUtils.isNotBlank(bo.getSerialNo()), QualityTraceability::getSerialNo, bo.getSerialNo());
        lqw.eq(bo.getWorkOrderId() != null, QualityTraceability::getWorkOrderId, bo.getWorkOrderId());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    public Long insertByBo(QualityTraceabilityBo bo) {
        validateTraceNo(bo.getTraceNo());
        QualityTraceability add = MapstructUtils.convert(bo, QualityTraceability.class);
        qualityTraceabilityMapper.insert(add);
        return add.getId();
    }

    public void updateByBo(QualityTraceabilityBo bo) {
        QualityTraceability update = MapstructUtils.convert(bo, QualityTraceability.class);
        qualityTraceabilityMapper.updateById(update);
    }

    public void deleteById(Long id) {
        qualityTraceabilityMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(id -> qualityTraceabilityMapper.deleteById(id));
    }

    public void validateTraceNo(String traceNo) {
        LambdaQueryWrapper<QualityTraceability> lqw = Wrappers.lambdaQuery();
        lqw.eq(QualityTraceability::getTraceNo, traceNo);
        QualityTraceability exist = qualityTraceabilityMapper.selectOne(lqw);
        Assert.isNull(exist, "追溯编号重复，请手动修改");
    }

}
