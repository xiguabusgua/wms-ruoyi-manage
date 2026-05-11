package com.ruoyi.wms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.EquipmentInfoBo;
import com.ruoyi.wms.domain.entity.EquipmentInfo;
import com.ruoyi.wms.domain.vo.EquipmentInfoVo;
import com.ruoyi.wms.mapper.EquipmentInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 设备台账Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class EquipmentInfoService {

    private final EquipmentInfoMapper equipmentInfoMapper;

    /**
     * 查询设备台账
     */
    public EquipmentInfoVo queryById(Long id) {
        return equipmentInfoMapper.selectVoById(id);
    }

    /**
     * 查询设备台账列表
     */
    public TableDataInfo<EquipmentInfoVo> queryPageList(EquipmentInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<EquipmentInfo> lqw = buildQueryWrapper(bo);
        Page<EquipmentInfoVo> result = equipmentInfoMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备台账列表
     */
    public List<EquipmentInfoVo> queryList(EquipmentInfoBo bo) {
        LambdaQueryWrapper<EquipmentInfo> lqw = buildQueryWrapper(bo);
        return equipmentInfoMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<EquipmentInfo> buildQueryWrapper(EquipmentInfoBo bo) {
        LambdaQueryWrapper<EquipmentInfo> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getEquipmentCode()), EquipmentInfo::getEquipmentCode, bo.getEquipmentCode());
        lqw.like(StringUtils.isNotBlank(bo.getEquipmentName()), EquipmentInfo::getEquipmentName, bo.getEquipmentName());
        lqw.eq(bo.getEquipmentType() != null, EquipmentInfo::getEquipmentType, bo.getEquipmentType());
        lqw.eq(bo.getCategoryId() != null, EquipmentInfo::getCategoryId, bo.getCategoryId());
        lqw.eq(bo.getEquipmentStatus() != null, EquipmentInfo::getEquipmentStatus, bo.getEquipmentStatus());
        lqw.like(StringUtils.isNotBlank(bo.getLocation()), EquipmentInfo::getLocation, bo.getLocation());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增设备台账
     */
    public void insertByBo(EquipmentInfoBo bo) {
        EquipmentInfo add = MapstructUtils.convert(bo, EquipmentInfo.class);
        equipmentInfoMapper.insert(add);
    }

    /**
     * 修改设备台账
     */
    public void updateByBo(EquipmentInfoBo bo) {
        EquipmentInfo update = MapstructUtils.convert(bo, EquipmentInfo.class);
        equipmentInfoMapper.updateById(update);
    }

    /**
     * 校验设备编码是否唯一
     */
    public boolean checkEquipmentCodeUnique(EquipmentInfoBo bo) {
        LambdaQueryWrapper<EquipmentInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(EquipmentInfo::getEquipmentCode, bo.getEquipmentCode());
        lqw.ne(bo.getId() != null, EquipmentInfo::getId, bo.getId());
        return equipmentInfoMapper.selectCount(lqw) == 0;
    }

    /**
     * 删除设备台账
     */
    public void deleteByIds(Collection<Long> ids) {
        equipmentInfoMapper.deleteBatchIds(ids);
    }

}
