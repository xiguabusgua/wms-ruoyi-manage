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
import com.ruoyi.wms.domain.bo.PurchaseRequisitionBo;
import com.ruoyi.wms.domain.vo.PurchaseRequisitionVo;
import com.ruoyi.wms.service.PurchaseRequisitionService;

import java.util.List;

/**
 * 采购申请
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/purchaseRequisition")
public class PurchaseRequisitionController extends BaseController {

    private final PurchaseRequisitionService purchaseRequisitionService;

    /**
     * 查询采购申请列表
     */
    @SaCheckPermission("wms:purchase:list")
    @GetMapping("/list")
    public TableDataInfo<PurchaseRequisitionVo> list(PurchaseRequisitionBo bo, PageQuery pageQuery) {
        return purchaseRequisitionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出采购申请列表
     */
    @SaCheckPermission("wms:purchase:export")
    @Log(title = "采购申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PurchaseRequisitionBo bo, HttpServletResponse response) {
        List<PurchaseRequisitionVo> list = purchaseRequisitionService.queryList(bo);
        ExcelUtil.exportExcel(list, "采购申请", PurchaseRequisitionVo.class, response);
    }

    /**
     * 获取采购申请详细信息
     */
    @SaCheckPermission("wms:purchase:query")
    @GetMapping("/{id}")
    public R<PurchaseRequisitionVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(purchaseRequisitionService.queryById(id));
    }

    /**
     * 新增采购申请
     */
    @SaCheckPermission("wms:purchase:add")
    @Log(title = "采购申请", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody PurchaseRequisitionBo bo) {
        Long id = purchaseRequisitionService.insertByBo(bo);
        return R.ok(id);
    }

    /**
     * 提交审批
     */
    @SaCheckPermission("wms:purchase:add")
    @Log(title = "采购申请-提交审批", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/submitApproval/{id}")
    public R<Void> submitApproval(@PathVariable Long id) {
        purchaseRequisitionService.submitForApproval(id);
        return R.ok();
    }

    /**
     * 审批通过
     */
    @SaCheckPermission("wms:purchase:approve")
    @Log(title = "采购申请-审批通过", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/approve/{id}")
    public R<Void> approve(@PathVariable Long id,
                           @RequestParam(required = false) Long approverId,
                           @RequestParam(required = false) String approveRemark) {
        purchaseRequisitionService.approve(id, approverId, approveRemark);
        return R.ok();
    }

    /**
     * 审批驳回
     */
    @SaCheckPermission("wms:purchase:approve")
    @Log(title = "采购申请-审批驳回", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/reject/{id}")
    public R<Void> reject(@PathVariable Long id,
                          @RequestParam(required = false) Long approverId,
                          @RequestParam String rejectReason) {
        purchaseRequisitionService.reject(id, approverId, rejectReason);
        return R.ok();
    }

    /**
     * 关闭采购申请
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购申请-关闭", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/close/{id}")
    public R<Void> close(@PathVariable Long id) {
        purchaseRequisitionService.closeRequisition(id);
        return R.ok();
    }

    /**
     * 修改采购申请
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购申请", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PurchaseRequisitionBo bo) {
        purchaseRequisitionService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除采购申请
     */
    @SaCheckPermission("wms:purchase:remove")
    @Log(title = "采购申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        purchaseRequisitionService.deleteById(id);
        return R.ok();
    }

}
