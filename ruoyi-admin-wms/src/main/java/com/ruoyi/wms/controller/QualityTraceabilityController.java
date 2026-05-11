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
import com.ruoyi.wms.domain.bo.QualityTraceabilityBo;
import com.ruoyi.wms.domain.vo.QualityTraceabilityVo;
import com.ruoyi.wms.service.QualityTraceabilityService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/qualityTraceability")
public class QualityTraceabilityController extends BaseController {

    private final QualityTraceabilityService qualityTraceabilityService;

    @SaCheckPermission("wms:trace:list")
    @GetMapping("/list")
    public TableDataInfo<QualityTraceabilityVo> list(QualityTraceabilityBo bo, PageQuery pageQuery) {
        return qualityTraceabilityService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:trace:export")
    @Log(title = "质量追溯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(QualityTraceabilityBo bo, HttpServletResponse response) {
        List<QualityTraceabilityVo> list = qualityTraceabilityService.queryList(bo);
        ExcelUtil.exportExcel(list, "质量追溯记录", QualityTraceabilityVo.class, response);
    }

    @SaCheckPermission("wms:trace:query")
    @GetMapping("/{id}")
    public R<QualityTraceabilityVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(qualityTraceabilityService.queryById(id));
    }

    @SaCheckPermission("wms:trace:add")
    @Log(title = "质量追溯", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody QualityTraceabilityBo bo) {
        return R.ok(qualityTraceabilityService.insertByBo(bo));
    }

    @SaCheckPermission("wms:trace:edit")
    @Log(title = "质量追溯", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody QualityTraceabilityBo bo) {
        qualityTraceabilityService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:trace:remove")
    @Log(title = "质量追溯", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable List<Long> ids) {
        qualityTraceabilityService.deleteByIds(ids);
        return R.ok();
    }

}
