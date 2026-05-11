package com.ruoyi.wms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.InventoryAlertBo;
import com.ruoyi.wms.domain.entity.InventoryAlert;
import com.ruoyi.wms.domain.vo.InventoryAlertVo;
import com.ruoyi.wms.mapper.InventoryAlertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 库存预警Service业务层处理
 *
 * @author zcc
 * @date 2024-08-15
 */
@RequiredArgsConstructor
@Service
public class InventoryAlertService {

    private final InventoryAlertMapper inventoryAlertMapper;

    /**
     * 查询库存预警
     */
    public InventoryAlertVo queryById(Long id) {
        return inventoryAlertMapper.selectVoById(id);
    }

    /**
     * 查询库存预警列表
     */
    public TableDataInfo<InventoryAlertVo> queryPageList(InventoryAlertBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<InventoryAlert> lqw = buildQueryWrapper(bo);
        Page<InventoryAlertVo> result = inventoryAlertMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询库存预警列表
     */
    public List<InventoryAlertVo> queryList(InventoryAlertBo bo) {
        LambdaQueryWrapper<InventoryAlert> lqw = buildQueryWrapper(bo);
        return inventoryAlertMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<InventoryAlert> buildQueryWrapper(InventoryAlertBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<InventoryAlert> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getItemId() != null, InventoryAlert::getItemId, bo.getItemId());
        lqw.eq(bo.getSkuId() != null, InventoryAlert::getSkuId, bo.getSkuId());
        lqw.eq(bo.getWarehouseId() != null, InventoryAlert::getWarehouseId, bo.getWarehouseId());
        lqw.eq(bo.getAlertType() != null, InventoryAlert::getAlertType, bo.getAlertType());
        lqw.eq(bo.getAlertLevel() != null, InventoryAlert::getAlertLevel, bo.getAlertLevel());
        lqw.eq(bo.getAlertStatus() != null, InventoryAlert::getAlertStatus, bo.getAlertStatus());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增库存预警
     */
    public void insertByBo(InventoryAlertBo bo) {
        InventoryAlert add = MapstructUtils.convert(bo, InventoryAlert.class);
        inventoryAlertMapper.insert(add);
    }

    /**
     * 修改库存预警
     */
    public void updateByBo(InventoryAlertBo bo) {
        InventoryAlert update = MapstructUtils.convert(bo, InventoryAlert.class);
        inventoryAlertMapper.updateById(update);
    }

    /**
     * 删除库存预警
     */
    public void deleteById(Long id) {
        inventoryAlertMapper.deleteById(id);
    }

    /**
     * 批量删除库存预警
     */
    public void deleteByIds(Collection<Long> ids) {
        inventoryAlertMapper.deleteBatchIds(ids);
    }
}
