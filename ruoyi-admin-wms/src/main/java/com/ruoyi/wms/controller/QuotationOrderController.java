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
import com.ruoyi.wms.domain.bo.QuotationOrderBo;
import com.ruoyi.wms.domain.vo.QuotationOrderVo;
import com.ruoyi.wms.service.QuotationOrderService;

import java.util.List;

/**
 * 报价单
 *
 * @author zcc
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/quotationOrder")
public class QuotationOrderController extends BaseController {

    private final QuotationOrderService quotationOrderService;

    /**
     * 查询报价单列表
     */
    @SaCheckPermission("wms:quotation:list")
    @GetMapping("/list")
    public TableDataInfo<QuotationOrderVo> list(QuotationOrderBo bo, PageQuery pageQuery) {
        return quotationOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * F2.2.5 报价历史 - 查询客户报价历史记录
     */
    @SaCheckPermission("wms:quotation:list")
    @GetMapping("/history")
    public TableDataInfo<QuotationOrderVo> history(QuotationOrderBo bo, PageQuery pageQuery) {
        return quotationOrderService.queryHistoryList(bo, pageQuery);
    }

    /**
     * 导出报价单列表
     */
    @SaCheckPermission("wms:quotation:export")
    @Log(title = "报价单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(QuotationOrderBo bo, HttpServletResponse response) {
        List<QuotationOrderVo> list = quotationOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "报价单", QuotationOrderVo.class, response);
    }

    /**
     * 获取报价单详细信息
     */
    @SaCheckPermission("wms:quotation:query")
    @GetMapping("/{id}")
    public R<QuotationOrderVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(quotationOrderService.queryById(id));
    }

    @SaCheckPermission("wms:quotation:query")
    @GetMapping("/getIdByNo")
    public R<Long> getIdByNo(@RequestParam String orderNo) {
        return R.ok(quotationOrderService.queryIdByOrderNo(orderNo));
    }

    /**
     * F2.2.1 报价单录入 - 新增报价单（草稿/暂存）
     */
    @SaCheckPermission("wms:quotation:add")
    @Log(title = "报价单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody QuotationOrderBo bo) {
        Long id = quotationOrderService.insertByBo(bo);
        return R.ok(id);
    }

    /**
     * F2.2.1 报价单录入 - 提交审批
     */
    @SaCheckPermission("wms:quotation:add")
    @Log(title = "报价单-提交审批", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/submitApproval/{id}")
    public R<Void> submitApproval(@PathVariable Long id) {
        quotationOrderService.submitForApproval(id);
        return R.ok();
    }

    /**
     * F2.2.3 报价审批 - 审批通过
     */
    @SaCheckPermission("wms:quotation:approve")
    @Log(title = "报价单-审批通过", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/approve/{id}")
    public R<Void> approve(@PathVariable Long id,
                           @RequestParam(required = false) Long approverId,
                           @RequestParam(required = false) String approveRemark) {
        quotationOrderService.approve(id, approverId, approveRemark);
        return R.ok();
    }

    /**
     * F2.2.3 报价审批 - 审批驳回
     */
    @SaCheckPermission("wms:quotation:approve")
    @Log(title = "报价单-审批驳回", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/reject/{id}")
    public R<Void> reject(@PathVariable Long id,
                          @RequestParam(required = false) Long approverId,
                          @RequestParam String rejectReason) {
        quotationOrderService.reject(id, approverId, rejectReason);
        return R.ok();
    }

    /**
     * F2.2.4 报价转订单 - 将已审批通过的报价单转为销售出库订单
     */
    @SaCheckPermission("wms:quotation:convert")
    @Log(title = "报价单-转订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/convertToShipmentOrder/{id}")
    public R<Void> convertToShipmentOrder(@PathVariable Long id) {
        quotationOrderService.convertToShipmentOrder(id);
        return R.ok();
    }

    /**
     * 标记报价单已失效
     */
    @SaCheckPermission("wms:quotation:edit")
    @Log(title = "报价单-标记失效", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/markExpired/{id}")
    public R<Void> markExpired(@PathVariable Long id) {
        quotationOrderService.markExpired(id);
        return R.ok();
    }

    /**
     * 修改报价单
     */
    @SaCheckPermission("wms:quotation:edit")
    @Log(title = "报价单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody QuotationOrderBo bo) {
        quotationOrderService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除报价单
     */
    @SaCheckPermission("wms:quotation:remove")
    @Log(title = "报价单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        quotationOrderService.deleteById(id);
        return R.ok();
    }
}
