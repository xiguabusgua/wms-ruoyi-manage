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
import com.ruoyi.wms.domain.bo.IpqcInspectionBo;
import com.ruoyi.wms.domain.entity.IpqcInspection;
import com.ruoyi.wms.domain.vo.IpqcInspectionVo;
import com.ruoyi.wms.mapper.IpqcInspectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IpqcInspectionService {

    private final IpqcInspectionMapper ipqcInspectionMapper;

    public IpqcInspectionVo queryById(Long id) {
        IpqcInspectionVo vo = ipqcInspectionMapper.selectVoById(id);
        Assert.notNull(vo, "过程检验记录不存在");
        return vo;
    }

    public TableDataInfo<IpqcInspectionVo> queryPageList(IpqcInspectionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IpqcInspection> lqw = buildQueryWrapper(bo);
        Page<IpqcInspectionVo> result = ipqcInspectionMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<IpqcInspectionVo> queryList(IpqcInspectionBo bo) {
        LambdaQueryWrapper<IpqcInspection> lqw = buildQueryWrapper(bo);
        return ipqcInspectionMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IpqcInspection> buildQueryWrapper(IpqcInspectionBo bo) {
        LambdaQueryWrapper<IpqcInspection> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTaskId() != null, IpqcInspection::getTaskId, bo.getTaskId());
        lqw.eq(bo.getInspectionSubType() != null, IpqcInspection::getInspectionSubType, bo.getInspectionSubType());
        lqw.eq(bo.getWorkOrderId() != null, IpqcInspection::getWorkOrderId, bo.getWorkOrderId());
        lqw.like(StringUtils.isNotBlank(bo.getStationName()), IpqcInspection::getStationName, bo.getStationName());
        lqw.eq(StringUtils.isNotBlank(bo.getIsStopLine()), IpqcInspection::getIsStopLine, bo.getIsStopLine());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    public Long insertByBo(IpqcInspectionBo bo) {
        IpqcInspection add = MapstructUtils.convert(bo, IpqcInspection.class);
        ipqcInspectionMapper.insert(add);
        return add.getId();
    }

    public void updateByBo(IpqcInspectionBo bo) {
        IpqcInspection update = MapstructUtils.convert(bo, IpqcInspection.class);
        ipqcInspectionMapper.updateById(update);
    }

    public void deleteById(Long id) {
        ipqcInspectionMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(id -> ipqcInspectionMapper.deleteById(id));
    }

}
