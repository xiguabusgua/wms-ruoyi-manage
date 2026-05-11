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
import com.ruoyi.wms.domain.bo.IqcInspectionBo;
import com.ruoyi.wms.domain.vo.IqcInspectionVo;
import com.ruoyi.wms.service.IqcInspectionService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/iqcInspection")
public class IqcInspectionController extends BaseController {

    private final IqcInspectionService iqcInspectionService;

    @SaCheckPermission("wms:iqc:list")
    @GetMapping("/list")
    public TableDataInfo<IqcInspectionVo> list(IqcInspectionBo bo, PageQuery pageQuery) {
        return iqcInspectionService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:iqc:export")
    @Log(title = "来料检验", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IqcInspectionBo bo, HttpServletResponse response) {
        List<IqcInspectionVo> list = iqcInspectionService.queryList(bo);
        ExcelUtil.exportExcel(list, "来料检验记录", IqcInspectionVo.class, response);
    }

    @SaCheckPermission("wms:iqc:query")
    @GetMapping("/{id}")
    public R<IqcInspectionVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iqcInspectionService.queryById(id));
    }

    @SaCheckPermission("wms:iqc:add")
    @Log(title = "来料检验", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody IqcInspectionBo bo) {
        return R.ok(iqcInspectionService.insertByBo(bo));
    }

    @SaCheckPermission("wms:iqc:edit")
    @Log(title = "来料检验", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IqcInspectionBo bo) {
        iqcInspectionService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:iqc:remove")
    @Log(title = "来料检验", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable List<Long> ids) {
        iqcInspectionService.deleteByIds(ids);
        return R.ok();
    }

}
