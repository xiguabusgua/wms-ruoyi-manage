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
import com.ruoyi.wms.domain.bo.PurchaseOrderDetailBo;
import com.ruoyi.wms.domain.vo.PurchaseOrderDetailVo;
import com.ruoyi.wms.service.PurchaseOrderDetailService;

import java.util.List;

/**
 * 采购订单明细
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/purchaseOrderDetail")
public class PurchaseOrderDetailController extends BaseController {

    private final PurchaseOrderDetailService purchaseOrderDetailService;

    /**
     * 查询采购订单明细列表
     */
    @SaCheckPermission("wms:purchase:list")
    @GetMapping("/list")
    public TableDataInfo<PurchaseOrderDetailVo> list(PurchaseOrderDetailBo bo, PageQuery pageQuery) {
        return purchaseOrderDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取采购订单明细详情
     */
    @SaCheckPermission("wms:purchase:query")
    @GetMapping("/{id}")
    public R<PurchaseOrderDetailVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(purchaseOrderDetailService.queryById(id));
    }

    /**
     * 新增采购订单明细
     */
    @SaCheckPermission("wms:purchase:add")
    @Log(title = "采购订单明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PurchaseOrderDetailBo bo) {
        purchaseOrderDetailService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改采购订单明细
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购订单明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PurchaseOrderDetailBo bo) {
        purchaseOrderDetailService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除采购订单明细
     */
    @SaCheckPermission("wms:purchase:remove")
    @Log(title = "采购订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        purchaseOrderDetailService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
