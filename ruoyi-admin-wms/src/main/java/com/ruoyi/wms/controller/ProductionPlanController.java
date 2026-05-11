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
import com.ruoyi.wms.domain.bo.ProductionPlanBo;
import com.ruoyi.wms.domain.vo.ProductionPlanVo;
import com.ruoyi.wms.service.ProductionPlanService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 生产计划
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/productionPlan")
public class ProductionPlanController extends BaseController {

    private final ProductionPlanService productionPlanService;

    /**
     * 查询生产计划列表
     */
    @SaCheckPermission("wms:productionPlan:list")
    @GetMapping("/list")
    public TableDataInfo<ProductionPlanVo> list(ProductionPlanBo bo, PageQuery pageQuery) {
        return productionPlanService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出生产计划列表
     */
    @SaCheckPermission("wms:productionPlan:export")
    @Log(title = "生产计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProductionPlanBo bo, HttpServletResponse response) {
        List<ProductionPlanVo> list = productionPlanService.queryList(bo);
        ExcelUtil.exportExcel(list, "生产计划", ProductionPlanVo.class, response);
    }

    /**
     * 获取生产计划详细信息
     */
    @SaCheckPermission("wms:productionPlan:query")
    @GetMapping("/{id}")
    public R<ProductionPlanVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(productionPlanService.queryById(id));
    }

    /**
     * 新增生产计划
     */
    @SaCheckPermission("wms:productionPlan:add")
    @Log(title = "生产计划", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody ProductionPlanBo bo) {
        return R.ok(productionPlanService.insertByBo(bo));
    }

    /**
     * 从销售订单生成生产计划
     */
    @SaCheckPermission("wms:productionPlan:add")
    @Log(title = "生产计划-从订单生成", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/generateFromOrder")
    public R<Long> generateFromOrder(@RequestParam Long salesOrderId,
                                     @RequestParam Long productId,
                                     @RequestParam BigDecimal planQuantity,
                                     @RequestParam(required = false) LocalDate planStartDate,
                                     @RequestParam(required = false) LocalDate planEndDate,
                                     @RequestParam(required = false, defaultValue = "2") Integer priority) {
        return R.ok(productionPlanService.generateFromSalesOrder(salesOrderId, productId, planQuantity, planStartDate, planEndDate, priority));
    }

    /**
     * 排产 - 待排产→已排产
     */
    @SaCheckPermission("wms:productionPlan:edit")
    @Log(title = "生产计划-排产", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/schedule/{id}")
    public R<Void> schedule(@PathVariable Long id) {
        productionPlanService.schedule(id);
        return R.ok();
    }

    /**
     * 开始生产 - 已排产→生产中
     */
    @SaCheckPermission("wms:productionPlan:edit")
    @Log(title = "生产计划-开始生产", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/startProduction/{id}")
    public R<Void> startProduction(@PathVariable Long id) {
        productionPlanService.startProduction(id);
        return R.ok();
    }

    /**
     * 完成计划 - 生产中→已完成
     */
    @SaCheckPermission("wms:productionPlan:edit")
    @Log(title = "生产计划-完成", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/complete/{id}")
    public R<Void> complete(@PathVariable Long id) {
        productionPlanService.complete(id);
        return R.ok();
    }

    /**
     * 关闭计划 - 任意非终态→已关闭
     */
    @SaCheckPermission("wms:productionPlan:edit")
    @Log(title = "生产计划-关闭", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/close/{id}")
    public R<Void> close(@PathVariable Long id) {
        productionPlanService.close(id);
        return R.ok();
    }

    /**
     * 修改生产计划
     */
    @SaCheckPermission("wms:productionPlan:edit")
    @Log(title = "生产计划", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProductionPlanBo bo) {
        productionPlanService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除生产计划
     */
    @SaCheckPermission("wms:productionPlan:remove")
    @Log(title = "生产计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        productionPlanService.deleteById(id);
        return R.ok();
    }

}
