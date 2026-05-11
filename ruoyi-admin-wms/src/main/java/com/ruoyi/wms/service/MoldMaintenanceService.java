package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.MoldMaintenanceBo;
import com.ruoyi.wms.domain.entity.MoldInfo;
import com.ruoyi.wms.domain.entity.MoldMaintenance;
import com.ruoyi.wms.domain.vo.MoldMaintenanceVo;
import com.ruoyi.wms.mapper.MoldInfoMapper;
import com.ruoyi.wms.mapper.MoldMaintenanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MoldMaintenanceService {

    private final MoldMaintenanceMapper moldMaintenanceMapper;
    private final MoldInfoMapper moldInfoMapper;

    public MoldMaintenanceVo queryById(Long id) {
        return moldMaintenanceMapper.selectVoById(id);
    }

    public TableDataInfo<MoldMaintenanceVo> queryPageList(MoldMaintenanceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MoldMaintenance> lqw = buildQueryWrapper(bo);
        Page<MoldMaintenanceVo> result = moldMaintenanceMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<MoldMaintenanceVo> queryList(MoldMaintenanceBo bo) {
        LambdaQueryWrapper<MoldMaintenance> lqw = buildQueryWrapper(bo);
        return moldMaintenanceMapper.selectVoList(lqw);
    }

    public List<MoldMaintenanceVo> queryByMoldId(Long moldId) {
        LambdaQueryWrapper<MoldMaintenance> lqw = Wrappers.lambdaQuery();
        lqw.eq(MoldMaintenance::getMoldId, moldId);
        lqw.orderByDesc(MoldMaintenance::getMaintenanceDate);
        return moldMaintenanceMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MoldMaintenance> buildQueryWrapper(MoldMaintenanceBo bo) {
        LambdaQueryWrapper<MoldMaintenance> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMoldId() != null, MoldMaintenance::getMoldId, bo.getMoldId());
        lqw.eq(bo.getMaintenanceType() != null, MoldMaintenance::getMaintenanceType, bo.getMaintenanceType());
        lqw.ge(bo.getMaintenanceDate() != null, MoldMaintenance::getMaintenanceDate, bo.getMaintenanceDate());
        lqw.le(bo.getNextDate() != null, MoldMaintenance::getNextDate, bo.getNextDate());
        lqw.like(StrUtil.isNotBlank(bo.getMaintainer()), MoldMaintenance::getMaintainer, bo.getMaintainer());
        lqw.orderByDesc(MoldMaintenance::getCreateTime);
        return lqw;
    }

    public void insertByBo(MoldMaintenanceBo bo) {
        validateMoldExist(bo.getMoldId());
        MoldMaintenance add = MapstructUtils.convert(bo, MoldMaintenance.class);
        if (add.getMaintenanceDate() == null) {
            add.setMaintenanceDate(LocalDate.now());
        }
        moldMaintenanceMapper.insert(add);
    }

    public void updateByBo(MoldMaintenanceBo bo) {
        if (bo.getMoldId() != null) {
            validateMoldExist(bo.getMoldId());
        }
        MoldMaintenance update = MapstructUtils.convert(bo, MoldMaintenance.class);
        moldMaintenanceMapper.updateById(update);
    }

    private void validateMoldExist(Long moldId) {
        MoldInfo mold = moldInfoMapper.selectById(moldId);
        Assert.notNull(mold, "模具不存在");
    }

    public TableDataInfo<MoldMaintenanceVo> queryExpiringList(Integer days, PageQuery pageQuery) {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(days);
        LambdaQueryWrapper<MoldMaintenance> lqw = Wrappers.lambdaQuery();
        lqw.ge(MoldMaintenance::getNextDate, today);
        lqw.le(MoldMaintenance::getNextDate, targetDate);
        lqw.orderByAsc(MoldMaintenance::getNextDate);
        Page<MoldMaintenanceVo> result = moldMaintenanceMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<MoldMaintenanceVo> queryOverdueList() {
        LocalDate today = LocalDate.now();
        LambdaQueryWrapper<MoldMaintenance> lqw = Wrappers.lambdaQuery();
        lqw.lt(MoldMaintenance::getNextDate, today);
        lqw.isNotNull(MoldMaintenance::getNextDate);
        lqw.orderByAsc(MoldMaintenance::getNextDate);
        return moldMaintenanceMapper.selectVoList(lqw);
    }

    public void deleteById(Long id) {
        moldMaintenanceMapper.deleteById(id);
    }

    public void deleteByIds(List<Long> ids) {
        moldMaintenanceMapper.deleteBatchIds(ids);
    }

}
