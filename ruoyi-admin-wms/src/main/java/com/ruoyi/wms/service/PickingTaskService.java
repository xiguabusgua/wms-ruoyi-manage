package com.ruoyi.wms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.base.BaseException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.PickingTaskBo;
import com.ruoyi.wms.domain.entity.PickingTask;
import com.ruoyi.wms.domain.vo.PickingTaskVo;
import com.ruoyi.wms.mapper.PickingTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 拣货任务Service业务层处理
 *
 * @author zcc
 * @date 2024-08-15
 */
@RequiredArgsConstructor
@Service
public class PickingTaskService {

    private final PickingTaskMapper pickingTaskMapper;

    /**
     * 查询拣货任务
     */
    public PickingTaskVo queryById(Long id) {
        return pickingTaskMapper.selectVoById(id);
    }

    /**
     * 查询拣货任务列表
     */
    public TableDataInfo<PickingTaskVo> queryPageList(PickingTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PickingTask> lqw = buildQueryWrapper(bo);
        Page<PickingTaskVo> result = pickingTaskMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询拣货任务列表
     */
    public List<PickingTaskVo> queryList(PickingTaskBo bo) {
        LambdaQueryWrapper<PickingTask> lqw = buildQueryWrapper(bo);
        return pickingTaskMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PickingTask> buildQueryWrapper(PickingTaskBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PickingTask> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getTaskNo()), PickingTask::getTaskNo, bo.getTaskNo());
        lqw.eq(bo.getShipmentOrderId() != null, PickingTask::getShipmentOrderId, bo.getShipmentOrderId());
        lqw.eq(bo.getWarehouseId() != null, PickingTask::getWarehouseId, bo.getWarehouseId());
        lqw.eq(bo.getPickStatus() != null, PickingTask::getPickStatus, bo.getPickStatus());
        lqw.like(StringUtils.isNotBlank(bo.getPicker()), PickingTask::getPicker, bo.getPicker());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增拣货任务
     */
    public void insertByBo(PickingTaskBo bo) {
        PickingTask add = MapstructUtils.convert(bo, PickingTask.class);
        pickingTaskMapper.insert(add);
    }

    /**
     * 修改拣货任务
     */
    public void updateByBo(PickingTaskBo bo) {
        PickingTask update = MapstructUtils.convert(bo, PickingTask.class);
        pickingTaskMapper.updateById(update);
    }

    /**
     * 删除拣货任务
     */
    public void deleteById(Long id) {
        pickingTaskMapper.deleteById(id);
    }

    /**
     * 批量删除拣货任务
     */
    public void deleteByIds(Collection<Long> ids) {
        pickingTaskMapper.deleteBatchIds(ids);
    }
}
