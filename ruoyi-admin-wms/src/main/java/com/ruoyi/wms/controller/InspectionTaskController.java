package com.ruoyi.wms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.common.idempotent.annotation.RepeatSubmit;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.web.core.BaseController;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.wms.domain.bo.InspectionTaskBo;
import com.ruoyi.wms.domain.vo.InspectionTaskVo;
import com.ruoyi.wms.service.InspectionTaskService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/inspectionTask")
public class InspectionTaskController extends BaseController {

    private final InspectionTaskService inspectionTaskService;

    @SaCheckPermission("wms:inspection:list")
    @GetMapping("/list")
    public TableDataInfo<InspectionTaskVo> list(InspectionTaskBo bo, PageQuery pageQuery) {
        return inspectionTaskService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:inspection:export")
    @Log(title = "检验任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(InspectionTaskBo bo, HttpServletResponse response) {
        List<InspectionTaskVo> list = inspectionTaskService.queryList(bo);
        ExcelUtil.exportExcel(list, "检验任务", InspectionTaskVo.class, response);
    }

    @SaCheckPermission("wms:inspection:query")
    @GetMapping("/{id}")
    public R<InspectionTaskVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(inspectionTaskService.queryById(id));
    }

    @SaCheckPermission("wms:inspection:add")
    @Log(title = "检验任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody InspectionTaskBo bo) {
        return R.ok(inspectionTaskService.insertByBo(bo));
    }

    @SaCheckPermission("wms:inspection:edit")
    @Log(title = "检验任务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody InspectionTaskBo bo) {
        inspectionTaskService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:inspection:remove")
    @Log(title = "检验任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable List<Long> ids) {
        inspectionTaskService.deleteByIds(ids);
        return R.ok();
    }

}
