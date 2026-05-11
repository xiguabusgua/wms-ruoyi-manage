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
import com.ruoyi.wms.domain.bo.MoldBorrowBo;
import com.ruoyi.wms.domain.entity.MoldBorrow;
import com.ruoyi.wms.domain.entity.MoldInfo;
import com.ruoyi.wms.domain.vo.MoldBorrowVo;
import com.ruoyi.wms.mapper.MoldBorrowMapper;
import com.ruoyi.wms.mapper.MoldInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MoldBorrowService {

    private final MoldBorrowMapper moldBorrowMapper;
    private final MoldInfoMapper moldInfoMapper;

    public MoldBorrowVo queryById(Long id) {
        return moldBorrowMapper.selectVoById(id);
    }

    public TableDataInfo<MoldBorrowVo> queryPageList(MoldBorrowBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MoldBorrow> lqw = buildQueryWrapper(bo);
        Page<MoldBorrowVo> result = moldBorrowMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<MoldBorrowVo> queryList(MoldBorrowBo bo) {
        LambdaQueryWrapper<MoldBorrow> lqw = buildQueryWrapper(bo);
        return moldBorrowMapper.selectVoList(lqw);
    }

    public List<MoldBorrowVo> queryByMoldId(Long moldId) {
        LambdaQueryWrapper<MoldBorrow> lqw = Wrappers.lambdaQuery();
        lqw.eq(MoldBorrow::getMoldId, moldId);
        lqw.orderByDesc(MoldBorrow::getBorrowDate);
        return moldBorrowMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MoldBorrow> buildQueryWrapper(MoldBorrowBo bo) {
        LambdaQueryWrapper<MoldBorrow> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMoldId() != null, MoldBorrow::getMoldId, bo.getMoldId());
        lqw.like(StrUtil.isNotBlank(bo.getBorrowNo()), MoldBorrow::getBorrowNo, bo.getBorrowNo());
        lqw.like(StrUtil.isNotBlank(bo.getBorrower()), MoldBorrow::getBorrower, bo.getBorrower());
        lqw.eq(bo.getBorrowStatus() != null, MoldBorrow::getBorrowStatus, bo.getBorrowStatus());
        lqw.orderByDesc(MoldBorrow::getCreateTime);
        return lqw;
    }

    public void validateBorrowNo(String borrowNo) {
        LambdaQueryWrapper<MoldBorrow> lqw = Wrappers.lambdaQuery();
        lqw.eq(MoldBorrow::getBorrowNo, borrowNo);
        MoldBorrow exist = moldBorrowMapper.selectOne(lqw);
        Assert.isNull(exist, "借用工单号已存在");
    }

    public void validateCanBorrow(Long moldId) {
        MoldInfo mold = moldInfoMapper.selectById(moldId);
        Assert.notNull(mold, "模具不存在");
        if (!mold.getMoldStatus().equals(0)) {
            throw new ServiceException("只有正常状态的模具才能借用，当前状态不允许借用");
        }
        LambdaQueryWrapper<MoldBorrow> activeLqw = Wrappers.lambdaQuery();
        activeLqw.eq(MoldBorrow::getMoldId, moldId);
        activeLqw.eq(MoldBorrow::getBorrowStatus, 0);
        Long activeCount = moldBorrowMapper.selectCount(activeLqw);
        if (activeCount > 0) {
            throw new ServiceException("该模具当前有未归还的借用记录，不能重复借用");
        }
    }

    @Transactional
    public void insertByBo(MoldBorrowBo bo) {
        validateMoldExist(bo.getMoldId());
        validateBorrowNo(bo.getBorrowNo());
        validateCanBorrow(bo.getMoldId());
        MoldBorrow add = MapstructUtils.convert(bo, MoldBorrow.class);
        if (add.getBorrowDate() == null) {
            add.setBorrowDate(LocalDateTime.now());
        }
        if (add.getBorrowStatus() == null) {
            add.setBorrowStatus(0);
        }
        moldBorrowMapper.insert(add);
        updateMoldStatusToBorrowed(bo.getMoldId());
    }

    public void updateByBo(MoldBorrowBo bo) {
        if (bo.getMoldId() != null) {
            validateMoldExist(bo.getMoldId());
        }
        MoldBorrow update = MapstructUtils.convert(bo, MoldBorrow.class);
        moldBorrowMapper.updateById(update);
    }

    private void validateMoldExist(Long moldId) {
        MoldInfo mold = moldInfoMapper.selectById(moldId);
        Assert.notNull(mold, "模具不存在");
    }

    private void updateMoldStatusToBorrowed(Long moldId) {
        LambdaUpdateWrapper<MoldInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MoldInfo::getId, moldId);
        wrapper.set(MoldInfo::getMoldStatus, 3);
        moldInfoMapper.update(null, wrapper);
    }

    private void restoreMoldStatusFromBorrowed(Long moldId) {
        LambdaUpdateWrapper<MoldInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MoldInfo::getId, moldId);
        wrapper.set(MoldInfo::getMoldStatus, 0);
        moldInfoMapper.update(null, wrapper);
    }

    @Transactional
    public void returnBorrow(Long id, String returnCheckResult) {
        MoldBorrowVo vo = queryById(id);
        Assert.notNull(vo, "借用记录不存在");
        if (!vo.getBorrowStatus().equals(0)) {
            throw new ServiceException("该借用记录已归还，无需重复操作");
        }
        LambdaUpdateWrapper<MoldBorrow> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MoldBorrow::getId, id);
        wrapper.set(MoldBorrow::getBorrowStatus, 1);
        wrapper.set(MoldBorrow::getReturnDate, LocalDateTime.now());
        if (StrUtil.isNotBlank(returnCheckResult)) {
            wrapper.set(MoldBorrow::getReturnCheckResult, returnCheckResult);
        }
        moldBorrowMapper.update(null, wrapper);
        restoreMoldStatusFromBorrowed(vo.getMoldId());
    }

    public void deleteById(Long id) {
        MoldBorrowVo vo = queryById(id);
        Assert.notNull(vo, "借用记录不存在");
        if (vo.getBorrowStatus().equals(0)) {
            restoreMoldStatusFromBorrowed(vo.getMoldId());
        }
        moldBorrowMapper.deleteById(id);
    }

    public void deleteByIds(List<Long> ids) {
        ids.forEach(this::deleteById);
    }

}
