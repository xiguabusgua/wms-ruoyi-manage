package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.MoldLifeRecordBo;
import com.ruoyi.wms.domain.entity.MoldInfo;
import com.ruoyi.wms.domain.entity.MoldLifeRecord;
import com.ruoyi.wms.domain.vo.MoldLifeRecordVo;
import com.ruoyi.wms.mapper.MoldInfoMapper;
import com.ruoyi.wms.mapper.MoldLifeRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MoldLifeRecordService {

    private final MoldLifeRecordMapper moldLifeRecordMapper;
    private final MoldInfoMapper moldInfoMapper;

    public MoldLifeRecordVo queryById(Long id) {
        return moldLifeRecordMapper.selectVoById(id);
    }

    public TableDataInfo<MoldLifeRecordVo> queryPageList(MoldLifeRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MoldLifeRecord> lqw = buildQueryWrapper(bo);
        Page<MoldLifeRecordVo> result = moldLifeRecordMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<MoldLifeRecordVo> queryList(MoldLifeRecordBo bo) {
        LambdaQueryWrapper<MoldLifeRecord> lqw = buildQueryWrapper(bo);
        return moldLifeRecordMapper.selectVoList(lqw);
    }

    public List<MoldLifeRecordVo> queryByMoldId(Long moldId) {
        LambdaQueryWrapper<MoldLifeRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(MoldLifeRecord::getMoldId, moldId);
        lqw.orderByDesc(MoldLifeRecord::getRecordDate);
        return moldLifeRecordMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MoldLifeRecord> buildQueryWrapper(MoldLifeRecordBo bo) {
        LambdaQueryWrapper<MoldLifeRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMoldId() != null, MoldLifeRecord::getMoldId, bo.getMoldId());
        lqw.ge(bo.getRecordDate() != null, MoldLifeRecord::getRecordDate, bo.getRecordDate());
        lqw.le(bo.getRecordDate() != null, MoldLifeRecord::getRecordDate, bo.getRecordDate());
        lqw.eq(bo.getSource() != null, MoldLifeRecord::getSource, bo.getSource());
        lqw.like(StrUtil.isNotBlank(bo.getRemark()), MoldLifeRecord::getRemark, bo.getRemark());
        lqw.orderByDesc(MoldLifeRecord::getCreateTime);
        return lqw;
    }

    @Transactional
    public void insertByBo(MoldLifeRecordBo bo) {
        validateMoldExist(bo.getMoldId());
        MoldLifeRecord add = MapstructUtils.convert(bo, MoldLifeRecord.class);
        if (add.getRecordDate() == null) {
            add.setRecordDate(LocalDate.now());
        }
        if (add.getSource() == null) {
            add.setSource(2);
        }
        BigDecimal lastAccumulateStroke = getLastAccumulateStroke(bo.getMoldId());
        BigDecimal newAccumulateStroke = (lastAccumulateStroke != null ? lastAccumulateStroke : BigDecimal.ZERO)
            .add(bo.getStrokeCount());
        add.setAccumulateStroke(newAccumulateStroke);
        moldLifeRecordMapper.insert(add);
        updateMoldLifeInfo(bo.getMoldId(), bo.getStrokeCount());
    }

    public void updateByBo(MoldLifeRecordBo bo) {
        if (bo.getMoldId() != null) {
            validateMoldExist(bo.getMoldId());
        }
        MoldLifeRecord update = MapstructUtils.convert(bo, MoldLifeRecord.class);
        moldLifeRecordMapper.updateById(update);
    }

    private void validateMoldExist(Long moldId) {
        MoldInfo mold = moldInfoMapper.selectById(moldId);
        Assert.notNull(mold, "模具不存在");
    }

    private BigDecimal getLastAccumulateStroke(Long moldId) {
        LambdaQueryWrapper<MoldLifeRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(MoldLifeRecord::getMoldId, moldId);
        lqw.orderByDesc(MoldLifeRecord::getRecordDate);
        lqw.last("LIMIT 1");
        MoldLifeRecord lastRecord = moldLifeRecordMapper.selectOne(lqw);
        return lastRecord != null ? lastRecord.getAccumulateStroke() : null;
    }

    @Transactional
    public void updateMoldLifeInfo(Long moldId, BigDecimal strokeCount) {
        MoldInfo mold = moldInfoMapper.selectById(moldId);
        if (mold == null) {
            return;
        }
        BigDecimal currentTotalStroke = mold.getTotalStroke() != null ? mold.getTotalStroke() : BigDecimal.ZERO;
        BigDecimal newTotalStroke = currentTotalStroke.add(strokeCount);
        BigDecimal newCurrentLife = newTotalStroke.divide(new BigDecimal("10000"), 4, java.math.RoundingMode.HALF_UP);
        com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<MoldInfo> wrapper =
            com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaUpdate();
        wrapper.eq(MoldInfo::getId, moldId);
        wrapper.set(MoldInfo::getTotalStroke, newTotalStroke);
        wrapper.set(MoldInfo::getCurrentLife, newCurrentLife);
        moldInfoMapper.update(null, wrapper);
    }

    @Transactional
    public void autoRecordFromPlc(Long moldId, BigDecimal strokeCount, Long workOrderId) {
        validateMoldExist(moldId);
        MoldLifeRecord record = new MoldLifeRecord();
        record.setMoldId(moldId);
        record.setStrokeCount(strokeCount);
        record.setSource(1);
        record.setWorkOrderId(workOrderId);
        record.setRecordDate(LocalDate.now());
        BigDecimal lastAccumulateStroke = getLastAccumulateStroke(moldId);
        BigDecimal newAccumulateStroke = (lastAccumulateStroke != null ? lastAccumulateStroke : BigDecimal.ZERO)
            .add(strokeCount);
        record.setAccumulateStroke(newAccumulateStroke);
        moldLifeRecordMapper.insert(record);
        updateMoldLifeInfo(moldId, strokeCount);
    }

    public void deleteById(Long id) {
        moldLifeRecordMapper.deleteById(id);
    }

    public void deleteByIds(List<Long> ids) {
        moldLifeRecordMapper.deleteBatchIds(ids);
    }

}
