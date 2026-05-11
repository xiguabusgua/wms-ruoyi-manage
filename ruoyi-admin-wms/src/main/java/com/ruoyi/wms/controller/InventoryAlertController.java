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
import com.ruoyi.wms.domain.bo.InventoryAlertBo;
import com.ruoyi.wms.domain.vo.InventoryAlertVo;
import com.ruoyi.wms.service.InventoryAlertService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存预警
 *
 * @author zcc
 * @date 2024-08-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/inventoryAlert")
public class InventoryAlertController extends BaseController {

    private final InventoryAlertService inventoryAlertService;

    /**
     * 查询库存预警列表
     */
    @SaCheckPermission("wms:inventory:all")
    @GetMapping("/list")
    public TableDataInfo<InventoryAlertVo> list(InventoryAlertBo bo, PageQuery pageQuery) {
        return inventoryAlertService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出库存预警列表
     */
    @SaCheckPermission("wms:inventory:all")
    @Log(title = "库存预警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(InventoryAlertBo bo, HttpServletResponse response) {
        List<InventoryAlertVo> list = inventoryAlertService.queryList(bo);
        ExcelUtil.exportExcel(list, "库存预警", InventoryAlertVo.class, response);
    }

    /**
     * 获取库存预警详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:inventory:all")
    @GetMapping("/{id}")
    public R<InventoryAlertVo> getInfo(@NotNull(message = "主键不能为空")
                                        @PathVariable Long id) {
        return R.ok(inventoryAlertService.queryById(id));
    }

    /**
     * 新增库存预警
     */
    @SaCheckPermission("wms:inventory:all")
    @Log(title = "库存预警", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody InventoryAlertBo bo) {
        inventoryAlertService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改库存预警
     */
    @SaCheckPermission("wms:inventory:all")
    @Log(title = "库存预警", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody InventoryAlertBo bo) {
        inventoryAlertService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除库存预警
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:inventory:all")
    @Log(title = "库存预警", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        inventoryAlertService.deleteById(id);
        return R.ok();
    }
}
