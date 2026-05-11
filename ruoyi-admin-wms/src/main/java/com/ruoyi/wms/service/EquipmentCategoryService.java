package com.ruoyi.wms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.EquipmentCategoryBo;
import com.ruoyi.wms.domain.entity.EquipmentCategory;
import com.ruoyi.wms.domain.vo.EquipmentCategoryVo;
import com.ruoyi.wms.mapper.EquipmentCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 设备分类Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class EquipmentCategoryService {

    private final EquipmentCategoryMapper equipmentCategoryMapper;

    /**
     * 查询设备分类
     */
    public EquipmentCategoryVo queryById(Long id) {
        return equipmentCategoryMapper.selectVoById(id);
    }

    /**
     * 查询设备分类列表
     */
    public TableDataInfo<EquipmentCategoryVo> queryPageList(EquipmentCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<EquipmentCategory> lqw = buildQueryWrapper(bo);
        Page<EquipmentCategoryVo> result = equipmentCategoryMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备分类列表
     */
    public List<EquipmentCategoryVo> queryList(EquipmentCategoryBo bo) {
        LambdaQueryWrapper<EquipmentCategory> lqw = buildQueryWrapper(bo);
        return equipmentCategoryMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<EquipmentCategory> buildQueryWrapper(EquipmentCategoryBo bo) {
        LambdaQueryWrapper<EquipmentCategory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, EquipmentCategory::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), EquipmentCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), EquipmentCategory::getStatus, bo.getStatus());
        lqw.orderByAsc(EquipmentCategory::getOrderNum);
        return lqw;
    }

    /**
     * 新增设备分类
     */
    public void insertByBo(EquipmentCategoryBo bo) {
        EquipmentCategory add = MapstructUtils.convert(bo, EquipmentCategory.class);
        equipmentCategoryMapper.insert(add);
    }

    /**
     * 修改设备分类
     */
    public void updateByBo(EquipmentCategoryBo bo) {
        EquipmentCategory update = MapstructUtils.convert(bo, EquipmentCategory.class);
        equipmentCategoryMapper.updateById(update);
    }

    /**
     * 删除设备分类
     */
    public void deleteByIds(Collection<Long> ids) {
        equipmentCategoryMapper.deleteBatchIds(ids);
    }

}
