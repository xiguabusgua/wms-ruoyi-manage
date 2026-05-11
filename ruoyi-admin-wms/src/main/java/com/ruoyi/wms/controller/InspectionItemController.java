package com.ruoyi.wms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.idempotent.annotation.RepeatSubmit;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.web.core.BaseController;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.wms.domain.bo.InspectionItemBo;
import com.ruoyi.wms.domain.vo.InspectionItemVo;
import com.ruoyi.wms.service.InspectionItemService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/inspectionItem")
public class InspectionItemController extends BaseController {

    private final InspectionItemService inspectionItemService;

    @SaCheckPermission("wms:inspection:query")
    @GetMapping("/list/byTaskId/{taskId}")
    public R<List<InspectionItemVo>> listByTaskId(@NotNull(message = "任务ID不能为空") @PathVariable Long taskId) {
        return R.ok(inspectionItemService.queryByTaskId(taskId));
    }

    @SaCheckPermission("wms:inspection:add")
    @Log(title = "检验项目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody InspectionItemBo bo) {
        inspectionItemService.saveItems(List.of(com.ruoyi.common.core.utils.MapstructUtils.convert(bo, com.ruoyi.wms.domain.entity.InspectionItem.class)));
        return R.ok();
    }

    @SaCheckPermission("wms:inspection:edit")
    @Log(title = "检验项目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody InspectionItemBo bo) {
        com.ruoyi.wms.domain.entity.InspectionItem update = com.ruoyi.common.core.utils.MapstructUtils.convert(bo, com.ruoyi.wms.domain.entity.InspectionItem.class);
        inspectionItemMapper.updateById(update);
        return R.ok();
    }

    @SaCheckPermission("wms:inspection:remove")
    @Log(title = "检验项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable List<Long> ids) {
        inspectionItemService.deleteByIds(ids);
        return R.ok();
    }

    private final com.ruoyi.wms.mapper.InspectionItemMapper inspectionItemMapper;

}
