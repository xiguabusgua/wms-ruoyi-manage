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
import com.ruoyi.wms.domain.bo.SalesOrderBo;
import com.ruoyi.wms.domain.vo.SalesOrderVo;
import com.ruoyi.wms.service.SalesOrderService;

import java.util.List;

/**
 * 销售订单
 *
 * @author zcc
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/salesOrder")
public class SalesOrderController extends BaseController {

    private final SalesOrderService salesOrderService;

    /**
     * 查询销售订单列表
     */
    @SaCheckPermission("wms:salesOrder:list")
    @GetMapping("/list")
    public TableDataInfo<SalesOrderVo> list(SalesOrderBo bo, PageQuery pageQuery) {
        return salesOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出销售订单列表
     */
    @SaCheckPermission("wms:salesOrder:list")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SalesOrderBo bo, HttpServletResponse response) {
        List<SalesOrderVo> list = salesOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "销售订单", SalesOrderVo.class, response);
    }

    /**
     * 获取销售订单详细信息
     */
    @SaCheckPermission("wms:salesOrder:list")
    @GetMapping("/{id}")
    public R<SalesOrderVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(salesOrderService.queryById(id));
    }

    /**
     * 按编号查询ID
     */
    @SaCheckPermission("wms:salesOrder:list")
    @GetMapping("/getIdByNo")
    public R<Long> getIdByNo(@RequestParam String orderNo) {
        return R.ok(salesOrderService.queryIdByOrderNo(orderNo));
    }

    /**
     * 新增销售订单
     */
    @SaCheckPermission("wms:salesOrder:add")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody SalesOrderBo bo) {
        Long id = salesOrderService.insertByBo(bo);
        return R.ok(id);
    }

    /**
     * 提交审批
     */
    @SaCheckPermission("wms:salesOrder:add")
    @Log(title = "销售订单-提交审批", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/submitApproval/{id}")
    public R<Void> submitApproval(@PathVariable Long id) {
        salesOrderService.submitForApproval(id);
        return R.ok();
    }

    /**
     * 审批通过
     */
    @SaCheckPermission("wms:salesOrder:approve")
    @Log(title = "销售订单-审批通过", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/approve/{id}")
    public R<Void> approve(@PathVariable Long id) {
        salesOrderService.approve(id);
        return R.ok();
    }

    /**
     * 审批驳回
     */
    @SaCheckPermission("wms:salesOrder:approve")
    @Log(title = "销售订单-审批驳回", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/reject/{id}")
    public R<Void> reject(@PathVariable Long id, @RequestParam(required = false) String reason) {
        salesOrderService.reject(id, reason);
        return R.ok();
    }

    /**
     * 开始生产
     */
    @SaCheckPermission("wms:salesOrder:edit")
    @Log(title = "销售订单-开始生产", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/startProduction/{id}")
    public R<Void> startProduction(@PathVariable Long id) {
        salesOrderService.startProduction(id);
        return R.ok();
    }

    /**
     * 发货
     */
    @SaCheckPermission("wms:salesOrder:edit")
    @Log(title = "销售订单-发货", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/ship/{id}")
    public R<Void> ship(@PathVariable Long id) {
        salesOrderService.ship(id);
        return R.ok();
    }

    /**
     * 完成
     */
    @SaCheckPermission("wms:salesOrder:edit")
    @Log(title = "销售订单-完成", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/complete/{id}")
    public R<Void> complete(@PathVariable Long id) {
        salesOrderService.complete(id);
        return R.ok();
    }

    /**
     * 取消
     */
    @SaCheckPermission("wms:salesOrder:edit")
    @Log(title = "销售订单-取消", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/cancel/{id}")
    public R<Void> cancel(@PathVariable Long id) {
        salesOrderService.cancel(id);
        return R.ok();
    }

    /**
     * 修改销售订单
     */
    @SaCheckPermission("wms:salesOrder:edit")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SalesOrderBo bo) {
        salesOrderService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除销售订单
     */
    @SaCheckPermission("wms:salesOrder:remove")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        salesOrderService.deleteById(id);
        return R.ok();
    }

}
