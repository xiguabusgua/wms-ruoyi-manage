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
import com.ruoyi.wms.domain.bo.MoldMaintenanceBo;
import com.ruoyi.wms.domain.vo.MoldMaintenanceVo;
import com.ruoyi.wms.service.MoldMaintenanceService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/moldMaintenance")
public class MoldMaintenanceController extends BaseController {

    private final MoldMaintenanceService moldMaintenanceService;

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/list")
    public TableDataInfo<MoldMaintenanceVo> list(MoldMaintenanceBo bo, PageQuery pageQuery) {
        return moldMaintenanceService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/listByMold/{moldId}")
    public R<List<MoldMaintenanceVo>> listByMold(@PathVariable Long moldId) {
        return R.ok(moldMaintenanceService.queryByMoldId(moldId));
    }

    @SaCheckPermission("wms:mold:export")
    @Log(title = "模具保养记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MoldMaintenanceBo bo, HttpServletResponse response) {
        List<MoldMaintenanceVo> list = moldMaintenanceService.queryList(bo);
        ExcelUtil.exportExcel(list, "模具保养记录", MoldMaintenanceVo.class, response);
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/{id}")
    public R<MoldMaintenanceVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(moldMaintenanceService.queryById(id));
    }

    @SaCheckPermission("wms:mold:add")
    @Log(title = "模具保养记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MoldMaintenanceBo bo) {
        moldMaintenanceService.insertByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具保养记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MoldMaintenanceBo bo) {
        moldMaintenanceService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/expiring")
    public TableDataInfo<MoldMaintenanceVo> expiring(@RequestParam(defaultValue = "7") Integer days,
                                                      PageQuery pageQuery) {
        return moldMaintenanceService.queryExpiringList(days, pageQuery);
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/overdue")
    public R<List<MoldMaintenanceVo>> overdue() {
        return R.ok(moldMaintenanceService.queryOverdueList());
    }

    @SaCheckPermission("wms:mold:remove")
    @Log(title = "模具保养记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        moldMaintenanceService.deleteById(id);
        return R.ok();
    }

}
