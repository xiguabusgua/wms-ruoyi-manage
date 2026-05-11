package com.ruoyi.wms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.wms.domain.bo.InspectionItemBo;
import com.ruoyi.wms.domain.entity.InspectionItem;
import com.ruoyi.wms.domain.vo.InspectionItemVo;
import com.ruoyi.wms.mapper.InspectionItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InspectionItemService {

    private final InspectionItemMapper inspectionItemMapper;

    public List<InspectionItemVo> queryByTaskId(Long taskId) {
        LambdaQueryWrapper<InspectionItem> lqw = Wrappers.lambdaQuery();
        lqw.eq(InspectionItem::getTaskId, taskId);
        return inspectionItemMapper.selectVoList(lqw);
    }

    public void saveItems(List<InspectionItem> items) {
        items.forEach(item -> inspectionItemMapper.insert(item));
    }

    public void deleteByTaskId(Long taskId) {
        LambdaQueryWrapper<InspectionItem> lqw = Wrappers.lambdaQuery();
        lqw.eq(InspectionItem::getTaskId, taskId);
        inspectionItemMapper.delete(lqw);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(id -> inspectionItemMapper.deleteById(id));
    }

}
