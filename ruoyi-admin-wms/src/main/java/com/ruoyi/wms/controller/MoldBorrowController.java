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
import com.ruoyi.wms.domain.bo.MoldBorrowBo;
import com.ruoyi.wms.domain.vo.MoldBorrowVo;
import com.ruoyi.wms.service.MoldBorrowService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/moldBorrow")
public class MoldBorrowController extends BaseController {

    private final MoldBorrowService moldBorrowService;

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/list")
    public TableDataInfo<MoldBorrowVo> list(MoldBorrowBo bo, PageQuery pageQuery) {
        return moldBorrowService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/listByMold/{moldId}")
    public R<List<MoldBorrowVo>> listByMold(@PathVariable Long moldId) {
        return R.ok(moldBorrowService.queryByMoldId(moldId));
    }

    @SaCheckPermission("wms:mold:export")
    @Log(title = "模具借用归还", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MoldBorrowBo bo, HttpServletResponse response) {
        List<MoldBorrowVo> list = moldBorrowService.queryList(bo);
        ExcelUtil.exportExcel(list, "模具借用归还", MoldBorrowVo.class, response);
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/{id}")
    public R<MoldBorrowVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(moldBorrowService.queryById(id));
    }

    @SaCheckPermission("wms:mold:add")
    @Log(title = "模具借用-借用", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MoldBorrowBo bo) {
        moldBorrowService.insertByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具借用-修改", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MoldBorrowBo bo) {
        moldBorrowService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具借用-归还", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/return/{id}")
    public R<Void> returnBorrow(@PathVariable Long id,
                                 @RequestParam(required = false) String returnCheckResult) {
        moldBorrowService.returnBorrow(id, returnCheckResult);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:remove")
    @Log(title = "模具借用归还", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        moldBorrowService.deleteById(id);
        return R.ok();
    }

}
