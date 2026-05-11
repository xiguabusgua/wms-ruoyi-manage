package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.InspectionItemBo;
import com.ruoyi.wms.domain.bo.InspectionTaskBo;
import com.ruoyi.wms.domain.entity.InspectionItem;
import com.ruoyi.wms.domain.entity.InspectionTask;
import com.ruoyi.wms.domain.vo.InspectionItemVo;
import com.ruoyi.wms.domain.vo.InspectionTaskVo;
import com.ruoyi.wms.mapper.InspectionTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InspectionTaskService {

    private final InspectionTaskMapper inspectionTaskMapper;
    private final InspectionItemService inspectionItemService;

    public InspectionTaskVo queryById(Long id) {
        InspectionTaskVo vo = inspectionTaskMapper.selectVoById(id);
        Assert.notNull(vo, "检验任务不存在");
        vo.setItems(inspectionItemService.queryByTaskId(id));
        return vo;
    }

    public TableDataInfo<InspectionTaskVo> queryPageList(InspectionTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<InspectionTask> lqw = buildQueryWrapper(bo);
        Page<InspectionTaskVo> result = inspectionTaskMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<InspectionTaskVo> queryList(InspectionTaskBo bo) {
        LambdaQueryWrapper<InspectionTask> lqw = buildQueryWrapper(bo);
        return inspectionTaskMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<InspectionTask> buildQueryWrapper(InspectionTaskBo bo) {
        LambdaQueryWrapper<InspectionTask> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTaskNo()), InspectionTask::getTaskNo, bo.getTaskNo());
        lqw.eq(bo.getInspectionType() != null, InspectionTask::getInspectionType, bo.getInspectionType());
        lqw.eq(bo.getTaskStatus() != null, InspectionTask::getTaskStatus, bo.getTaskStatus());
        lqw.eq(bo.getProductId() != null, InspectionTask::getProductId, bo.getProductId());
        lqw.like(StringUtils.isNotBlank(bo.getInspector()), InspectionTask::getInspector, bo.getInspector());
        lqw.eq(StringUtils.isNotBlank(bo.getSourceOrderNo()), InspectionTask::getSourceOrderNo, bo.getSourceOrderNo());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    @Transactional
    public Long insertByBo(InspectionTaskBo bo) {
        validateTaskNo(bo.getTaskNo());
        InspectionTask add = MapstructUtils.convert(bo, InspectionTask.class);
        inspectionTaskMapper.insert(add);
        bo.setId(add.getId());
        saveItems(add.getId(), bo.getItems());
        return add.getId();
    }

    @Transactional
    public void updateByBo(InspectionTaskBo bo) {
        InspectionTask update = MapstructUtils.convert(bo, InspectionTask.class);
        inspectionTaskMapper.updateById(update);
        inspectionItemService.deleteByTaskId(bo.getId());
        List<InspectionItem> itemList = MapstructUtils.convert(bo.getItems(), InspectionItem.class);
        if (itemList != null) {
            itemList.forEach(it -> it.setTaskId(bo.getId()));
            inspectionItemService.saveItems(itemList);
        }
    }

    public void deleteById(Long id) {
        inspectionItemService.deleteByTaskId(id);
        inspectionTaskMapper.deleteById(id);
    }

    public void deleteByIds(List<Long> ids) {
        ids.forEach(id -> {
            inspectionItemService.deleteByTaskId(id);
            inspectionTaskMapper.deleteById(id);
        });
    }

    public void validateTaskNo(String taskNo) {
        LambdaQueryWrapper<InspectionTask> lqw = Wrappers.lambdaQuery();
        lqw.eq(InspectionTask::getTaskNo, taskNo);
        InspectionTask exist = inspectionTaskMapper.selectOne(lqw);
        Assert.isNull(exist, "任务编号重复，请手动修改");
    }

    private void saveItems(Long taskId, List<InspectionItemBo> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        List<InspectionItem> itemList = MapstructUtils.convert(items, InspectionItem.class);
        itemList.forEach(it -> it.setTaskId(taskId));
        inspectionItemService.saveItems(itemList);
    }

}
