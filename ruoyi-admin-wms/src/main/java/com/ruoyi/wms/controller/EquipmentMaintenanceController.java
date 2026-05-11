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
import com.ruoyi.wms.domain.bo.EquipmentMaintenanceBo;
import com.ruoyi.wms.domain.vo.EquipmentMaintenanceVo;
import com.ruoyi.wms.service.EquipmentMaintenanceService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 设备维护记录
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/equipmentMaintenance")
public class EquipmentMaintenanceController extends BaseController {

    private final EquipmentMaintenanceService equipmentMaintenanceService;

    /**
     * 查询设备维护记录列表
     */
    @SaCheckPermission("wms:equipment:list")
    @GetMapping("/list")
    public TableDataInfo<EquipmentMaintenanceVo> list(EquipmentMaintenanceBo bo, PageQuery pageQuery) {
        return equipmentMaintenanceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备维护记录列表
     */
    @SaCheckPermission("wms:equipment:export")
    @Log(title = "设备维护记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(EquipmentMaintenanceBo bo, HttpServletResponse response) {
        List<EquipmentMaintenanceVo> list = equipmentMaintenanceService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备维护记录", EquipmentMaintenanceVo.class, response);
    }

    /**
     * 获取设备维护记录详情
     */
    @SaCheckPermission("wms:equipment:query")
    @GetMapping("/{id}")
    public R<EquipmentMaintenanceVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(equipmentMaintenanceService.queryById(id));
    }

    /**
     * 新增设备维护记录
     */
    @SaCheckPermission("wms:equipment:add")
    @Log(title = "设备维护记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody EquipmentMaintenanceBo bo) {
        equipmentMaintenanceService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改设备维护记录
     */
    @SaCheckPermission("wms:equipment:edit")
    @Log(title = "设备维护记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody EquipmentMaintenanceBo bo) {
        equipmentMaintenanceService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 完成维护任务
     */
    @SaCheckPermission("wms:equipment:edit")
    @Log(title = "设备维护-完成", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/complete/{id}")
    public R<Void> complete(@PathVariable Long id,
                            @RequestParam(required = false) BigDecimal actualCost) {
        equipmentMaintenanceService.completeMaintenance(id, actualCost);
        return R.ok();
    }

    /**
     * 删除设备维护记录
     */
    @SaCheckPermission("wms:equipment:remove")
    @Log(title = "设备维护记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        equipmentMaintenanceService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
