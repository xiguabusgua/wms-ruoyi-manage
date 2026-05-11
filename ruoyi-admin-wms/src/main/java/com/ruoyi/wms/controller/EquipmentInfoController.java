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
import com.ruoyi.wms.domain.bo.EquipmentInfoBo;
import com.ruoyi.wms.domain.vo.EquipmentInfoVo;
import com.ruoyi.wms.service.EquipmentInfoService;

import java.util.List;

/**
 * 设备台账
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/equipmentInfo")
public class EquipmentInfoController extends BaseController {

    private final EquipmentInfoService equipmentInfoService;

    /**
     * 查询设备台账列表
     */
    @SaCheckPermission("wms:equipment:list")
    @GetMapping("/list")
    public TableDataInfo<EquipmentInfoVo> list(EquipmentInfoBo bo, PageQuery pageQuery) {
        return equipmentInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备台账列表
     */
    @SaCheckPermission("wms:equipment:export")
    @Log(title = "设备台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(EquipmentInfoBo bo, HttpServletResponse response) {
        List<EquipmentInfoVo> list = equipmentInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备台账", EquipmentInfoVo.class, response);
    }

    /**
     * 获取设备台账详细信息
     */
    @SaCheckPermission("wms:equipment:query")
    @GetMapping("/{id}")
    public R<EquipmentInfoVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(equipmentInfoService.queryById(id));
    }

    /**
     * 新增设备台账
     */
    @SaCheckPermission("wms:equipment:add")
    @Log(title = "设备台账", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody EquipmentInfoBo bo) {
        if (!equipmentInfoService.checkEquipmentCodeUnique(bo)) {
            return R.fail("设备编码已存在");
        }
        equipmentInfoService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改设备台账
     */
    @SaCheckPermission("wms:equipment:edit")
    @Log(title = "设备台账", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody EquipmentInfoBo bo) {
        if (!equipmentInfoService.checkEquipmentCodeUnique(bo)) {
            return R.fail("设备编码已存在");
        }
        equipmentInfoService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除设备台账
     */
    @SaCheckPermission("wms:equipment:remove")
    @Log(title = "设备台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        equipmentInfoService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
