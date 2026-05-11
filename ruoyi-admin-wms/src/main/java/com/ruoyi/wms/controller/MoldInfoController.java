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
import com.ruoyi.wms.domain.bo.MoldInfoBo;
import com.ruoyi.wms.domain.vo.MoldInfoVo;
import com.ruoyi.wms.service.MoldInfoService;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/moldInfo")
public class MoldInfoController extends BaseController {

    private final MoldInfoService moldInfoService;

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/list")
    public TableDataInfo<MoldInfoVo> list(MoldInfoBo bo, PageQuery pageQuery) {
        return moldInfoService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:mold:export")
    @Log(title = "模具台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MoldInfoBo bo, HttpServletResponse response) {
        List<MoldInfoVo> list = moldInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "模具台账", MoldInfoVo.class, response);
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/{id}")
    public R<MoldInfoVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(moldInfoService.queryById(id));
    }

    @SaCheckPermission("wms:mold:add")
    @Log(title = "模具台账", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody MoldInfoBo bo) {
        return R.ok(moldInfoService.insertByBo(bo));
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具台账", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MoldInfoBo bo) {
        moldInfoService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具状态变更-维修中", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/changeStatus/{id}/{status}")
    public R<Void> changeStatus(@PathVariable Long id, @PathVariable Integer status) {
        moldInfoService.changeStatus(id, status);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/checkLifeWarning/{id}")
    public R<Void> checkLifeWarning(@PathVariable Long id,
                                     @RequestParam(defaultValue = "0.8") BigDecimal warningRatio) {
        moldInfoService.checkLifeWarning(id, warningRatio);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:remove")
    @Log(title = "模具台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        moldInfoService.deleteById(id);
        return R.ok();
    }

}
