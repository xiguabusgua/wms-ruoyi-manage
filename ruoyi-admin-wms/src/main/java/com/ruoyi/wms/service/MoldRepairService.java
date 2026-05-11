package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.MoldRepairBo;
import com.ruoyi.wms.domain.entity.MoldInfo;
import com.ruoyi.wms.domain.entity.MoldRepair;
import com.ruoyi.wms.domain.vo.MoldRepairVo;
import com.ruoyi.wms.mapper.MoldInfoMapper;
import com.ruoyi.wms.mapper.MoldRepairMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MoldRepairService {

    private final MoldRepairMapper moldRepairMapper;
    private final MoldInfoMapper moldInfoMapper;

    public MoldRepairVo queryById(Long id) {
        return moldRepairMapper.selectVoById(id);
    }

    public TableDataInfo<MoldRepairVo> queryPageList(MoldRepairBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MoldRepair> lqw = buildQueryWrapper(bo);
        Page<MoldRepairVo> result = moldRepairMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<MoldRepairVo> queryList(MoldRepairBo bo) {
        LambdaQueryWrapper<MoldRepair> lqw = buildQueryWrapper(bo);
        return moldRepairMapper.selectVoList(lqw);
    }

    public List<MoldRepairVo> queryByMoldId(Long moldId) {
        LambdaQueryWrapper<MoldRepair> lqw = Wrappers.lambdaQuery();
        lqw.eq(MoldRepair::getMoldId, moldId);
        lqw.orderByDesc(MoldRepair::getCreateTime);
        return moldRepairMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MoldRepair> buildQueryWrapper(MoldRepairBo bo) {
        LambdaQueryWrapper<MoldRepair> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMoldId() != null, MoldRepair::getMoldId, bo.getMoldId());
        lqw.like(StrUtil.isNotBlank(bo.getRepairApplyNo()), MoldRepair::getRepairApplyNo, bo.getRepairApplyNo());
        lqw.eq(bo.getRepairStatus() != null, MoldRepair::getRepairStatus, bo.getRepairStatus());
        lqw.like(StrUtil.isNotBlank(bo.getRepairer()), MoldRepair::getRepairer, bo.getRepairer());
        lqw.orderByDesc(MoldRepair::getCreateTime);
        return lqw;
    }

    public void validateRepairApplyNo(String repairApplyNo) {
        LambdaQueryWrapper<MoldRepair> lqw = Wrappers.lambdaQuery();
        lqw.eq(MoldRepair::getRepairApplyNo, repairApplyNo);
        MoldRepair exist = moldRepairMapper.selectOne(lqw);
        Assert.isNull(exist, "维修申请单号已存在");
    }

    @Transactional
    public void insertByBo(MoldRepairBo bo) {
        validateMoldExist(bo.getMoldId());
        validateRepairApplyNo(bo.getRepairApplyNo());
        validateMoldStatusForRepair(bo.getMoldId());
        MoldRepair add = MapstructUtils.convert(bo, MoldRepair.class);
        if (add.getRepairStatus() == null) {
            add.setRepairStatus(0);
        }
        if (add.getRepairStartDate() == null) {
            add.setRepairStartDate(LocalDate.now());
        }
        moldRepairMapper.insert(add);
        updateMoldStatusToRepairing(bo.getMoldId());
    }

    @Transactional
    public void updateByBo(MoldRepairBo bo) {
        MoldRepair existing = moldRepairMapper.selectById(bo.getId());
        Assert.notNull(existing, "维修记录不存在");
        if (bo.getMoldId() != null && !existing.getMoldId().equals(bo.getMoldId())) {
            validateMoldExist(bo.getMoldId());
        }
        MoldRepair update = MapstructUtils.convert(bo, MoldRepair.class);
        moldRepairMapper.updateById(update);
    }

    private void validateMoldExist(Long moldId) {
        MoldInfo mold = moldInfoMapper.selectById(moldId);
        Assert.notNull(mold, "模具不存在");
    }

    private void validateMoldStatusForRepair(Long moldId) {
        MoldInfo mold = moldInfoMapper.selectById(moldId);
        Assert.notNull(mold, "模具不存在");
        if (!mold.getMoldStatus().equals(0)) {
            throw new ServiceException("只有正常状态的模具才能发起维修");
        }
    }

    private void updateMoldStatusToRepairing(Long moldId) {
        LambdaUpdateWrapper<MoldInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MoldInfo::getId, moldId);
        wrapper.set(MoldInfo::getMoldStatus, 1);
        moldInfoMapper.update(null, wrapper);
    }

    @Transactional
    public void completeRepair(Long id, String repairContent, String replacedParts,
                                BigDecimal cost, String repairer) {
        MoldRepairVo vo = queryById(id);
        Assert.notNull(vo, "维修记录不存在");
        if (!vo.getRepairStatus().equals(0)) {
            throw new ServiceException("该维修记录已完成，无需重复操作");
        }
        LambdaUpdateWrapper<MoldRepair> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MoldRepair::getId, id);
        wrapper.set(MoldRepair::getRepairStatus, 1);
        wrapper.set(MoldRepair::getRepairEndDate, LocalDate.now());
        if (StrUtil.isNotBlank(repairContent)) {
            wrapper.set(MoldRepair::getRepairContent, repairContent);
        }
        if (StrUtil.isNotBlank(replacedParts)) {
            wrapper.set(MoldRepair::getReplacedParts, replacedParts);
        }
        if (cost != null) {
            wrapper.set(MoldRepair::getCost, cost);
        }
        if (StrUtil.isNotBlank(repairer)) {
            wrapper.set(MoldRepair::getRepairer, repairer);
        }
        moldRepairMapper.update(null, wrapper);
        restoreMoldStatus(vo.getMoldId());
    }

    private void restoreMoldStatus(Long moldId) {
        LambdaUpdateWrapper<MoldInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MoldInfo::getId, moldId);
        wrapper.set(MoldInfo::getMoldStatus, 0);
        moldInfoMapper.update(null, wrapper);
    }

    public void deleteById(Long id) {
        MoldRepairVo vo = queryById(id);
        Assert.notNull(vo, "维修记录不存在");
        if (vo.getRepairStatus().equals(0)) {
            restoreMoldStatus(vo.getMoldId());
        }
        moldRepairMapper.deleteById(id);
    }

    public void deleteByIds(List<Long> ids) {
        ids.forEach(this::deleteById);
    }

}
