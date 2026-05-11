package com.ruoyi.wms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.idempotent.annotation.RepeatSubmit;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.web.core.BaseController;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.wms.domain.bo.QuotationOrderDetailBo;
import com.ruoyi.wms.domain.vo.QuotationOrderDetailVo;
import com.ruoyi.wms.service.QuotationOrderDetailService;

/**
 * 报价单明细
 *
 * @author zcc
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/quotationOrderDetail")
public class QuotationOrderDetailController extends BaseController {

    private final QuotationOrderDetailService quotationOrderDetailService;

    /**
     * 查询报价单明细列表
     */
    @SaCheckPermission("wms:quotation:list")
    @GetMapping("/list")
    public TableDataInfo<QuotationOrderDetailVo> list(QuotationOrderDetailBo bo, PageQuery pageQuery) {
        return quotationOrderDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取报价单明细详情
     */
    @SaCheckPermission("wms:quotation:query")
    @GetMapping("/{id}")
    public R<QuotationOrderDetailVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(quotationOrderDetailService.queryById(id));
    }

    /**
     * 新增报价单明细
     */
    @SaCheckPermission("wms:quotation:add")
    @Log(title = "报价单明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody QuotationOrderDetailBo bo) {
        quotationOrderDetailService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改报价单明细
     */
    @SaCheckPermission("wms:quotation:edit")
    @Log(title = "报价单明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody QuotationOrderDetailBo bo) {
        quotationOrderDetailService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除报价单明细
     */
    @SaCheckPermission("wms:quotation:remove")
    @Log(title = "报价单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        quotationOrderDetailService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }
}
