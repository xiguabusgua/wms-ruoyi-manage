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
import com.ruoyi.wms.domain.bo.PurchaseOrderBo;
import com.ruoyi.wms.domain.vo.PurchaseOrderVo;
import com.ruoyi.wms.service.PurchaseOrderService;

import java.util.List;

/**
 * 采购订单
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/purchaseOrder")
public class PurchaseOrderController extends BaseController {

    private final PurchaseOrderService purchaseOrderService;

    /**
     * 查询采购订单列表
     */
    @SaCheckPermission("wms:purchase:list")
    @GetMapping("/list")
    public TableDataInfo<PurchaseOrderVo> list(PurchaseOrderBo bo, PageQuery pageQuery) {
        return purchaseOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出采购订单列表
     */
    @SaCheckPermission("wms:purchase:export")
    @Log(title = "采购订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PurchaseOrderBo bo, HttpServletResponse response) {
        List<PurchaseOrderVo> list = purchaseOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "采购订单", PurchaseOrderVo.class, response);
    }

    /**
     * 获取采购订单详细信息
     */
    @SaCheckPermission("wms:purchase:query")
    @GetMapping("/{id}")
    public R<PurchaseOrderVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(purchaseOrderService.queryById(id));
    }

    /**
     * 新增采购订单
     */
    @SaCheckPermission("wms:purchase:add")
    @Log(title = "采购订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody PurchaseOrderBo bo) {
        Long id = purchaseOrderService.insertByBo(bo);
        return R.ok(id);
    }

    /**
     * 审批通过
     */
    @SaCheckPermission("wms:purchase:approve")
    @Log(title = "采购订单-审批通过", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/approve/{id}")
    public R<Void> approve(@PathVariable Long id) {
        purchaseOrderService.approve(id);
        return R.ok();
    }

    /**
     * 部分到货
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购订单-部分到货", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/partialReceive/{id}")
    public R<Void> partialReceive(@PathVariable Long id) {
        purchaseOrderService.partialReceive(id);
        return R.ok();
    }

    /**
     * 全部到货
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购订单-全部到货", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/fullReceive/{id}")
    public R<Void> fullReceive(@PathVariable Long id) {
        purchaseOrderService.fullReceive(id);
        return R.ok();
    }

    /**
     * 关闭采购订单
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购订单-关闭", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/close/{id}")
    public R<Void> close(@PathVariable Long id) {
        purchaseOrderService.closeOrder(id);
        return R.ok();
    }

    /**
     * 取消采购订单
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购订单-取消", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/cancel/{id}")
    public R<Void> cancel(@PathVariable Long id) {
        purchaseOrderService.cancelOrder(id);
        return R.ok();
    }

    /**
     * 修改采购订单
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PurchaseOrderBo bo) {
        purchaseOrderService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除采购订单
     */
    @SaCheckPermission("wms:purchase:remove")
    @Log(title = "采购订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        purchaseOrderService.deleteById(id);
        return R.ok();
    }

}
