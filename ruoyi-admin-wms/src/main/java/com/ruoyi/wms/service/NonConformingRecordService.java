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
import com.ruoyi.wms.domain.bo.NonConformingRecordBo;
import com.ruoyi.wms.domain.entity.NonConformingRecord;
import com.ruoyi.wms.domain.vo.NonConformingRecordVo;
import com.ruoyi.wms.mapper.NonConformingRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NonConformingRecordService {

    private final NonConformingRecordMapper nonConformingRecordMapper;

    public NonConformingRecordVo queryById(Long id) {
        NonConformingRecordVo vo = nonConformingRecordMapper.selectVoById(id);
        Assert.notNull(vo, "不良记录不存在");
        return vo;
    }

    public TableDataInfo<NonConformingRecordVo> queryPageList(NonConformingRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<NonConformingRecord> lqw = buildQueryWrapper(bo);
        Page<NonConformingRecordVo> result = nonConformingRecordMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<NonConformingRecordVo> queryList(NonConformingRecordBo bo) {
        LambdaQueryWrapper<NonConformingRecord> lqw = buildQueryWrapper(bo);
        return nonConformingRecordMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<NonConformingRecord> buildQueryWrapper(NonConformingRecordBo bo) {
        LambdaQueryWrapper<NonConformingRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getRecordNo()), NonConformingRecord::getRecordNo, bo.getRecordNo());
        lqw.eq(bo.getInspectionType() != null, NonConformingRecord::getInspectionType, bo.getInspectionType());
        lqw.eq(bo.getInspectionTaskId() != null, NonConformingRecord::getInspectionTaskId, bo.getInspectionTaskId());
        lqw.eq(bo.getProductId() != null, NonConformingRecord::getProductId, bo.getProductId());
        lqw.like(StringUtils.isNotBlank(bo.getDefectType()), NonConformingRecord::getDefectType, bo.getDefectType());
        lqw.eq(bo.getSeverity() != null, NonConformingRecord::getSeverity, bo.getSeverity());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    public Long insertByBo(NonConformingRecordBo bo) {
        validateRecordNo(bo.getRecordNo());
        NonConformingRecord add = MapstructUtils.convert(bo, NonConformingRecord.class);
        nonConformingRecordMapper.insert(add);
        return add.getId();
    }

    public void updateByBo(NonConformingRecordBo bo) {
        NonConformingRecord update = MapstructUtils.convert(bo, NonConformingRecord.class);
        nonConformingRecordMapper.updateById(update);
    }

    public void deleteById(Long id) {
        nonConformingRecordMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(id -> nonConformingRecordMapper.deleteById(id));
    }

    public void validateRecordNo(String recordNo) {
        LambdaQueryWrapper<NonConformingRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(NonConformingRecord::getRecordNo, recordNo);
        NonConformingRecord exist = nonConformingRecordMapper.selectOne(lqw);
        Assert.isNull(exist, "记录编号重复，请手动修改");
    }

}
