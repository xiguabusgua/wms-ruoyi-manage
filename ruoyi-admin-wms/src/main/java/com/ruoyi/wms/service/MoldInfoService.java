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
import com.ruoyi.wms.domain.bo.MoldInfoBo;
import com.ruoyi.wms.domain.entity.MoldInfo;
import com.ruoyi.wms.domain.vo.MoldInfoVo;
import com.ruoyi.wms.mapper.MoldInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MoldInfoService {

    private final MoldInfoMapper moldInfoMapper;
    private final MoldBorrowService moldBorrowService;

    public MoldInfoVo queryById(Long id) {
        return moldInfoMapper.selectVoById(id);
    }

    public TableDataInfo<MoldInfoVo> queryPageList(MoldInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MoldInfo> lqw = buildQueryWrapper(bo);
        Page<MoldInfoVo> result = moldInfoMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<MoldInfoVo> queryList(MoldInfoBo bo) {
        LambdaQueryWrapper<MoldInfo> lqw = buildQueryWrapper(bo);
        return moldInfoMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MoldInfo> buildQueryWrapper(MoldInfoBo bo) {
        LambdaQueryWrapper<MoldInfo> lqw = Wrappers.lambdaQuery();
        lqw.like(StrUtil.isNotBlank(bo.getMoldCode()), MoldInfo::getMoldCode, bo.getMoldCode());
        lqw.like(StrUtil.isNotBlank(bo.getMoldName()), MoldInfo::getMoldName, bo.getMoldName());
        lqw.eq(bo.getMoldType() != null, MoldInfo::getMoldType, bo.getMoldType());
        lqw.eq(bo.getMoldStatus() != null, MoldInfo::getMoldStatus, bo.getMoldStatus());
        lqw.eq(bo.getProductId() != null, MoldInfo::getProductId, bo.getProductId());
        lqw.like(StrUtil.isNotBlank(bo.getManufacturer()), MoldInfo::getManufacturer, bo.getManufacturer());
        lqw.orderByDesc(MoldInfo::getCreateTime);
        return lqw;
    }

    public void validateMoldCode(String moldCode) {
        LambdaQueryWrapper<MoldInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(MoldInfo::getMoldCode, moldCode);
        MoldInfo exist = moldInfoMapper.selectOne(lqw);
        Assert.isNull(exist, "模具编号已存在");
    }

    @Transactional
    public Long insertByBo(MoldInfoBo bo) {
        validateMoldCode(bo.getMoldCode());
        if (bo.getMoldStatus() == null) {
            bo.setMoldStatus(0);
        }
        MoldInfo add = MapstructUtils.convert(bo, MoldInfo.class);
        moldInfoMapper.insert(add);
        return add.getId();
    }

    @Transactional
    public void updateByBo(MoldInfoBo bo) {
        MoldInfo exist = moldInfoMapper.selectById(bo.getId());
        Assert.notNull(exist, "模具不存在");
        if (!exist.getMoldCode().equals(bo.getMoldCode())) {
            validateMoldCode(bo.getMoldCode());
        }
        MoldInfo update = MapstructUtils.convert(bo, MoldInfo.class);
        moldInfoMapper.updateById(update);
    }

    public void checkLifeWarning(Long id, BigDecimal warningRatio) {
        MoldInfoVo vo = queryById(id);
        Assert.notNull(vo, "模具不存在");
        if (vo.getDesignLife() == null || vo.getCurrentLife() == null) {
            return;
        }
        BigDecimal threshold = vo.getDesignLife().multiply(warningRatio);
        if (vo.getCurrentLife().compareTo(threshold) >= 0) {
            throw new ServiceException("模具【" + vo.getMoldName() + "】当前寿命已达到设计寿命的" + warningRatio.multiply(new BigDecimal("100")).intValue() + "%，请注意预警");
        }
    }

    public void changeStatus(Long id, Integer targetStatus) {
        MoldInfoVo vo = queryById(id);
        Assert.notNull(vo, "模具不存在");
        Integer currentStatus = vo.getMoldStatus();
        if (currentStatus.equals(targetStatus)) {
            throw new ServiceException("模具当前已是目标状态，无需变更");
        }
        validateStatusTransition(currentStatus, targetStatus);
        LambdaUpdateWrapper<MoldInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MoldInfo::getId, id);
        wrapper.set(MoldInfo::getMoldStatus, targetStatus);
        moldInfoMapper.update(null, wrapper);
    }

    private void validateStatusTransition(Integer currentStatus, Integer targetStatus) {
        switch (targetStatus) {
            case 1:
                if (!currentStatus.equals(0)) {
                    throw new ServiceException("只有正常状态的模具才能设置为维修中");
                }
                break;
            case 2:
                break;
            case 3:
                if (!currentStatus.equals(0)) {
                    throw new ServiceException("只有正常状态的模具才能借出");
                }
                break;
            case 0:
                if (currentStatus.equals(2)) {
                    throw new ServiceException("已报废的模具无法恢复为正常状态");
                }
                break;
            default:
                throw new ServiceException("无效的目标状态");
        }
    }

    public void deleteById(Long id) {
        MoldInfoVo vo = queryById(id);
        Assert.notNull(vo, "模具不存在");
        if (vo.getMoldStatus().equals(3)) {
            throw new ServiceException("模具【" + vo.getMoldCode() + "】正在借用中，无法删除");
        }
        if (vo.getMoldStatus().equals(1)) {
            throw new ServiceException("模具【" + vo.getMoldCode() + "】正在维修中，无法删除");
        }
        moldInfoMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional
    public void updateLifeInfo(Long moldId, BigDecimal strokeCount) {
        MoldInfo mold = moldInfoMapper.selectById(moldId);
        Assert.notNull(mold, "模具不存在");
        BigDecimal currentTotalStroke = mold.getTotalStroke() != null ? mold.getTotalStroke() : BigDecimal.ZERO;
        BigDecimal newTotalStroke = currentTotalStroke.add(strokeCount);
        BigDecimal newCurrentLife = newTotalStroke.divide(new BigDecimal("10000"), 4, java.math.RoundingMode.HALF_UP);
        LambdaUpdateWrapper<MoldInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MoldInfo::getId, moldId);
        wrapper.set(MoldInfo::getTotalStroke, newTotalStroke);
        wrapper.set(MoldInfo::getCurrentLife, newCurrentLife);
        moldInfoMapper.update(null, wrapper);
    }

}
