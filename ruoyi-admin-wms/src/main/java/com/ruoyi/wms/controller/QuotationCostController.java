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
import com.ruoyi.wms.domain.bo.QuotationCostBo;
import com.ruoyi.wms.domain.vo.QuotationCostVo;
import com.ruoyi.wms.service.QuotationCostService;

import java.util.List;

/**
 * 成本核算明细
 *
 * @author zcc
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/quotationCost")
public class QuotationCostController extends BaseController {

    private final QuotationCostService quotationCostService;

    /**
     * 查询成本核算明细列表
     */
    @SaCheckPermission("wms:quotation:query")
    @GetMapping("/list")
    public TableDataInfo<QuotationCostVo> list(QuotationCostBo bo, PageQuery pageQuery) {
        return quotationCostService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出成本核算明细
     */
    @SaCheckPermission("wms:quotation:export")
    @Log(title = "成本核算明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(QuotationCostBo bo, HttpServletResponse response) {
        List<QuotationCostVo> list = quotationCostService.queryList(bo);
        ExcelUtil.exportExcel(list, "成本核算明细", QuotationCostVo.class, response);
    }

    /**
     * 获取成本核算明细详情
     */
    @SaCheckPermission("wms:quotation:query")
    @GetMapping("/{id}")
    public R<QuotationCostVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(quotationCostService.queryById(id));
    }

    /**
     * F2.2.2 成本核算 - 保存成本核算数据
     */
    @SaCheckPermission("wms:quotation:add")
    @Log(title = "成本核算", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/saveCalculation/{quotationOrderId}")
    public R<Void> saveCalculation(@PathVariable Long quotationOrderId,
                                   @RequestBody List<QuotationCostBo> costBos) {
        quotationCostService.saveCostCalculation(quotationOrderId, costBos);
        return R.ok();
    }

    /**
     * 新增成本核算明细
     */
    @SaCheckPermission("wms:quotation:add")
    @Log(title = "成本核算明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody QuotationCostBo bo) {
        quotationCostService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改成本核算明细
     */
    @SaCheckPermission("wms:quotation:edit")
    @Log(title = "成本核算明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody QuotationCostBo bo) {
        quotationCostService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除成本核算明细
     */
    @SaCheckPermission("wms:quotation:remove")
    @Log(title = "成本核算明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        quotationCostService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }
}
