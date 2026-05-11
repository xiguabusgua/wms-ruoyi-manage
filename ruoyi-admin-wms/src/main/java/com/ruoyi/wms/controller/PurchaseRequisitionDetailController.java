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
import com.ruoyi.wms.domain.bo.PurchaseRequisitionDetailBo;
import com.ruoyi.wms.domain.vo.PurchaseRequisitionDetailVo;
import com.ruoyi.wms.service.PurchaseRequisitionDetailService;

import java.util.List;

/**
 * 采购申请明细
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/purchaseRequisitionDetail")
public class PurchaseRequisitionDetailController extends BaseController {

    private final PurchaseRequisitionDetailService purchaseRequisitionDetailService;

    /**
     * 查询采购申请明细列表
     */
    @SaCheckPermission("wms:purchase:list")
    @GetMapping("/list")
    public TableDataInfo<PurchaseRequisitionDetailVo> list(PurchaseRequisitionDetailBo bo, PageQuery pageQuery) {
        return purchaseRequisitionDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取采购申请明细详情
     */
    @SaCheckPermission("wms:purchase:query")
    @GetMapping("/{id}")
    public R<PurchaseRequisitionDetailVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(purchaseRequisitionDetailService.queryById(id));
    }

    /**
     * 新增采购申请明细
     */
    @SaCheckPermission("wms:purchase:add")
    @Log(title = "采购申请明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PurchaseRequisitionDetailBo bo) {
        purchaseRequisitionDetailService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改采购申请明细
     */
    @SaCheckPermission("wms:purchase:edit")
    @Log(title = "采购申请明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PurchaseRequisitionDetailBo bo) {
        purchaseRequisitionDetailService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除采购申请明细
     */
    @SaCheckPermission("wms:purchase:remove")
    @Log(title = "采购申请明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        purchaseRequisitionDetailService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
