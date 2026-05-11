package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.BomVersionBo;
import com.ruoyi.wms.domain.entity.BomVersion;
import com.ruoyi.wms.domain.vo.BomVersionVo;
import com.ruoyi.wms.mapper.BomVersionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * BOM版本记录Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class BomVersionService {

    private final BomVersionMapper bomVersionMapper;

    /**
     * 查询BOM版本记录
     */
    public BomVersionVo queryById(Long id) {
        return bomVersionMapper.selectVoById(id);
    }

    /**
     * 查询BOM版本记录列表
     */
    public TableDataInfo<BomVersionVo> queryPageList(BomVersionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BomVersion> lqw = buildQueryWrapper(bo);
        Page<BomVersionVo> result = bomVersionMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询BOM版本记录列表
     */
    public List<BomVersionVo> queryList(BomVersionBo bo) {
        LambdaQueryWrapper<BomVersion> lqw = buildQueryWrapper(bo);
        return bomVersionMapper.selectVoList(lqw);
    }

    /**
     * 根据BOM主表ID查询所有版本记录
     */
    public List<BomVersionVo> queryByBomId(Long bomId) {
        LambdaQueryWrapper<BomVersion> lqw = Wrappers.lambdaQuery();
        lqw.eq(BomVersion::getBomId, bomId);
        lqw.orderByDesc(BomVersion::getChangeTime);
        return bomVersionMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BomVersion> buildQueryWrapper(BomVersionBo bo) {
        LambdaQueryWrapper<BomVersion> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getBomId() != null, BomVersion::getBomId, bo.getBomId());
        lqw.orderByDesc(BomVersion::getChangeTime);
        return lqw;
    }

    /**
     * 新增BOM版本记录
     */
    public void insertByBo(BomVersionBo bo) {
        BomVersion add = MapstructUtils.convert(bo, BomVersion.class);
        if (add.getChangeTime() == null) {
            add.setChangeTime(LocalDateTime.now());
        }
        bomVersionMapper.insert(add);
    }

    /**
     * 直接插入实体（供内部调用）
     */
    public void insertEntity(BomVersion entity) {
        bomVersionMapper.insert(entity);
    }

    /**
     * 删除BOM版本记录
     */
    public void deleteByIds(Collection<Long> ids) {
        bomVersionMapper.deleteBatchIds(ids);
    }

    /**
     * 根据BOM主表ID删除版本记录
     */
    public void deleteByBomId(@jakarta.validation.constraints.NotNull Long bomId) {
        LambdaQueryWrapper<BomVersion> lqw = Wrappers.lambdaQuery();
        lqw.eq(BomVersion::getBomId, bomId);
        bomVersionMapper.delete(lqw);
    }

}
