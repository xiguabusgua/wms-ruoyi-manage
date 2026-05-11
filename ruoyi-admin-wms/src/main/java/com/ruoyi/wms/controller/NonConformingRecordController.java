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
import com.ruoyi.wms.domain.bo.NonConformingRecordBo;
import com.ruoyi.wms.domain.vo.NonConformingRecordVo;
import com.ruoyi.wms.service.NonConformingRecordService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/nonConformingRecord")
public class NonConformingRecordController extends BaseController {

    private final NonConformingRecordService nonConformingRecordService;

    @SaCheckPermission("wms:ncr:list")
    @GetMapping("/list")
    public TableDataInfo<NonConformingRecordVo> list(NonConformingRecordBo bo, PageQuery pageQuery) {
        return nonConformingRecordService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:ncr:export")
    @Log(title = "不良记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(NonConformingRecordBo bo, HttpServletResponse response) {
        List<NonConformingRecordVo> list = nonConformingRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "不良记录", NonConformingRecordVo.class, response);
    }

    @SaCheckPermission("wms:ncr:query")
    @GetMapping("/{id}")
    public R<NonConformingRecordVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(nonConformingRecordService.queryById(id));
    }

    @SaCheckPermission("wms:ncr:add")
    @Log(title = "不良记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody NonConformingRecordBo bo) {
        return R.ok(nonConformingRecordService.insertByBo(bo));
    }

    @SaCheckPermission("wms:ncr:edit")
    @Log(title = "不良记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody NonConformingRecordBo bo) {
        nonConformingRecordService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:ncr:remove")
    @Log(title = "不良记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable List<Long> ids) {
        nonConformingRecordService.deleteByIds(ids);
        return R.ok();
    }

}
