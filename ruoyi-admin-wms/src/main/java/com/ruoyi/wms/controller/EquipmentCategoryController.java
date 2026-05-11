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
import com.ruoyi.wms.domain.bo.EquipmentCategoryBo;
import com.ruoyi.wms.domain.vo.EquipmentCategoryVo;
import com.ruoyi.wms.service.EquipmentCategoryService;

import java.util.List;

/**
 * 设备分类
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/equipmentCategory")
public class EquipmentCategoryController extends BaseController {

    private final EquipmentCategoryService equipmentCategoryService;

    /**
     * 查询设备分类列表
     */
    @SaCheckPermission("wms:equipment:list")
    @GetMapping("/list")
    public TableDataInfo<EquipmentCategoryVo> list(EquipmentCategoryBo bo, PageQuery pageQuery) {
        return equipmentCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询所有启用的设备分类（树形结构）
     */
    @SaCheckPermission("wms:equipment:query")
    @GetMapping("/tree")
    public R<List<EquipmentCategoryVo>> tree(EquipmentCategoryBo bo) {
        List<EquipmentCategoryVo> list = equipmentCategoryService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出设备分类列表
     */
    @SaCheckPermission("wms:equipment:export")
    @Log(title = "设备分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(EquipmentCategoryBo bo, HttpServletResponse response) {
        List<EquipmentCategoryVo> list = equipmentCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备分类", EquipmentCategoryVo.class, response);
    }

    /**
     * 获取设备分类详细信息
     */
    @SaCheckPermission("wms:equipment:query")
    @GetMapping("/{id}")
    public R<EquipmentCategoryVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(equipmentCategoryService.queryById(id));
    }

    /**
     * 新增设备分类
     */
    @SaCheckPermission("wms:equipment:add")
    @Log(title = "设备分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody EquipmentCategoryBo bo) {
        equipmentCategoryService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改设备分类
     */
    @SaCheckPermission("wms:equipment:edit")
    @Log(title = "设备分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody EquipmentCategoryBo bo) {
        equipmentCategoryService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除设备分类
     */
    @SaCheckPermission("wms:equipment:remove")
    @Log(title = "设备分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        equipmentCategoryService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
