package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.base.BaseException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.BomDetailBo;
import com.ruoyi.wms.domain.bo.BomMainBo;
import com.ruoyi.wms.domain.entity.BomDetail;
import com.ruoyi.wms.domain.entity.BomMain;
import com.ruoyi.wms.domain.vo.BomDetailVo;
import com.ruoyi.wms.domain.vo.BomMainVo;
import com.ruoyi.wms.mapper.BomMainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * BOM主表Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class BomMainService {

    private final BomMainMapper bomMainMapper;
    private final BomDetailService bomDetailService;
    private final BomVersionService bomVersionService;

    /**
     * 查询BOM主表（含明细）
     */
    public BomMainVo queryById(Long id) {
        BomMainVo vo = bomMainMapper.selectVoById(id);
        Assert.notNull(vo, "BOM不存在");
        vo.setDetails(bomDetailService.queryByBomId(id));
        return vo;
    }

    /**
     * 查询BOM主表列表
     */
    public TableDataInfo<BomMainVo> queryPageList(BomMainBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BomMain> lqw = buildQueryWrapper(bo);
        Page<BomMainVo> result = bomMainMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询BOM主表列表
     */
    public List<BomMainVo> queryList(BomMainBo bo) {
        LambdaQueryWrapper<BomMain> lqw = buildQueryWrapper(bo);
        return bomMainMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BomMain> buildQueryWrapper(BomMainBo bo) {
        LambdaQueryWrapper<BomMain> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getBomCode()), BomMain::getBomCode, bo.getBomCode());
        lqw.eq(bo.getProductId() != null, BomMain::getProductId, bo.getProductId());
        lqw.eq(bo.getBomStatus() != null, BomMain::getBomStatus, bo.getBomStatus());
        lqw.like(StringUtils.isNotBlank(bo.getDescription()), BomMain::getDescription, bo.getDescription());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增BOM（草稿状态）
     */
    @Transactional
    public Long insertByBo(BomMainBo bo) {
        validateBomCode(bo.getBomCode());
        if (bo.getBomStatus() == null) {
            bo.setBomStatus(0);
        }
        BomMain add = MapstructUtils.convert(bo, BomMain.class);
        bomMainMapper.insert(add);
        bo.setId(add.getId());
        saveDetails(add.getId(), bo.getDetails());
        addVersionRecord(add.getId(), "V" + (bo.getBomVersion() != null ? bo.getBomVersion() : "1.0"), "新建BOM");
        return add.getId();
    }

    /**
     * 修改BOM
     */
    @Transactional
    public void updateByBo(BomMainBo bo) {
        BomMain update = MapstructUtils.convert(bo, BomMain.class);
        bomMainMapper.updateById(update);
        handleDetailChanges(bo.getId(), bo.getDetails());
        List<BomDetail> detailList = MapstructUtils.convert(bo.getDetails(), BomDetail.class);
        detailList.forEach(it -> it.setBomId(bo.getId()));
        bomDetailService.saveDetails(detailList);
    }

    private void handleDetailChanges(Long bomId, List<BomDetailBo> details) {
        if (details == null) {
            return;
        }
        List<BomDetailVo> dbList = bomDetailService.queryByBomId(bomId);
        var ids = details.stream()
            .filter(it -> it.getId() != null)
            .map(it -> it.getId())
            .toList();
        var delList = dbList.stream()
            .filter(it -> !ids.contains(it.getId()))
            .toList();
        if (CollUtil.isNotEmpty(delList)) {
            bomDetailService.deleteByIds(delList.stream().map(BomDetailVo::getId).toList());
        }
    }

    /**
     * 发布BOM（草稿->正式）
     */
    @Transactional
    public void publish(Long id) {
        BomMainVo vo = queryById(id);
        Assert.notNull(vo, "BOM不存在");
        if (vo.getBomStatus() == null || vo.getBomStatus() != 0) {
            throw new BaseException("只有草稿状态的BOM可以发布");
        }
        if (CollUtil.isEmpty(vo.getDetails())) {
            throw new BaseException("BOM明细不能为空，请添加子件后发布");
        }
        LambdaUpdateWrapper<BomMain> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(BomMain::getId, id);
        wrapper.set(BomMain::getBomStatus, 1);
        bomMainMapper.update(null, wrapper);
        String versionNo = vo.getBomVersion() != null ? vo.getBomVersion() : "1.0";
        addVersionRecord(id, versionNo, "发布BOM为正式版本");
    }

    /**
     * 使BOM失效（正式->失效）
     */
    @Transactional
    public void expire(Long id) {
        LambdaUpdateWrapper<BomMain> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(BomMain::getId, id);
        wrapper.eq(BomMain::getBomStatus, 1);
        wrapper.set(BomMain::getBomStatus, 2);
        bomMainMapper.update(null, wrapper);
        addVersionRecord(id, "", "使BOM失效");
    }

    /**
     * 升级BOM版本
     */
    @Transactional
    public void upgradeVersion(Long id, String newVersion, String changeDesc) {
        BomMainVo vo = queryById(id);
        Assert.notNull(vo, "BOM不存在");
        LambdaUpdateWrapper<BomMain> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(BomMain::getId, id);
        wrapper.set(BomMain::getBomVersion, newVersion);
        wrapper.set(BomMain::getBomStatus, 0);
        bomMainMapper.update(null, wrapper);
        addVersionRecord(id, newVersion, changeDesc);
    }

    /**
     * 删除BOM
     */
    @Transactional
    public void deleteById(Long id) {
        validateBeforeDelete(id);
        bomDetailService.deleteByBomId(id);
        bomVersionService.deleteByBomId(id);
        bomMainMapper.deleteById(id);
    }

    private void validateBeforeDelete(Long id) {
        BomMainVo vo = queryById(id);
        Assert.notNull(vo, "BOM不存在");
        Integer status = vo.getBomStatus();
        if (status != null && status == 1) {
            throw new BaseException("BOM【" + vo.getBomCode() + "】已正式发布，无法删除！");
        }
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(this::deleteById);
    }

    public void validateBomCode(String bomCode) {
        LambdaQueryWrapper<BomMain> lqw = Wrappers.lambdaQuery();
        lqw.eq(BomMain::getBomCode, bomCode);
        BomMain exist = bomMainMapper.selectOne(lqw);
        Assert.isNull(exist, "BOM编码重复，请手动修改");
    }

    /**
     * 新增版本记录
     */
    private void addVersionRecord(Long bomId, String versionNo, String changeDesc) {
        BomVersion version = new BomVersion();
        version.setBomId(bomId);
        version.setVersionNo(versionNo);
        version.setChangeDesc(changeDesc);
        version.setChangeTime(LocalDateTime.now());
        bomVersionService.insertEntity(version);
    }

    private void saveDetails(Long bomId, List<BomDetailBo> details) {
        if (CollUtil.isEmpty(details)) {
            return;
        }
        List<BomDetail> detailList = MapstructUtils.convert(details, BomDetail.class);
        detailList.forEach(it -> it.setBomId(bomId));
        bomDetailService.saveDetails(detailList);
    }

}
