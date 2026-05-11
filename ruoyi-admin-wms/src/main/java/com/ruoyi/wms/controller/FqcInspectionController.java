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
import com.ruoyi.wms.domain.bo.FqcInspectionBo;
import com.ruoyi.wms.domain.vo.FqcInspectionVo;
import com.ruoyi.wms.service.FqcInspectionService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/fqcInspection")
public class FqcInspectionController extends BaseController {

    private final FqcInspectionService fqcInspectionService;

    @SaCheckPermission("wms:fqc:list")
    @GetMapping("/list")
    public TableDataInfo<FqcInspectionVo> list(FqcInspectionBo bo, PageQuery pageQuery) {
        return fqcInspectionService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:fqc:export")
    @Log(title = "成品检验", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FqcInspectionBo bo, HttpServletResponse response) {
        List<FqcInspectionVo> list = fqcInspectionService.queryList(bo);
        ExcelUtil.exportExcel(list, "成品检验记录", FqcInspectionVo.class, response);
    }

    @SaCheckPermission("wms:fqc:query")
    @GetMapping("/{id}")
    public R<FqcInspectionVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(fqcInspectionService.queryById(id));
    }

    @SaCheckPermission("wms:fqc:add")
    @Log(title = "成品检验", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody FqcInspectionBo bo) {
        return R.ok(fqcInspectionService.insertByBo(bo));
    }

    @SaCheckPermission("wms:fqc:edit")
    @Log(title = "成品检验", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FqcInspectionBo bo) {
        fqcInspectionService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:fqc:remove")
    @Log(title = "成品检验", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable List<Long> ids) {
        fqcInspectionService.deleteByIds(ids);
        return R.ok();
    }

}
