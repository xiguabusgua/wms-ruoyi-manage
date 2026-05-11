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
import com.ruoyi.wms.domain.bo.ProductionDailyReportBo;
import com.ruoyi.wms.domain.vo.ProductionDailyReportVo;
import com.ruoyi.wms.service.ProductionDailyReportService;

import java.util.List;

/**
 * 生产日报
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/productionDailyReport")
public class ProductionDailyReportController extends BaseController {

    private final ProductionDailyReportService productionDailyReportService;

    /**
     * 查询生产日报列表
     */
    @SaCheckPermission("wms:productionDailyReport:list")
    @GetMapping("/list")
    public TableDataInfo<ProductionDailyReportVo> list(ProductionDailyReportBo bo, PageQuery pageQuery) {
        return productionDailyReportService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出生产日报列表
     */
    @SaCheckPermission("wms:productionDailyReport:export")
    @Log(title = "生产日报", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProductionDailyReportBo bo, HttpServletResponse response) {
        List<ProductionDailyReportVo> list = productionDailyReportService.queryList(bo);
        ExcelUtil.exportExcel(list, "生产日报", ProductionDailyReportVo.class, response);
    }

    /**
     * 获取生产日报详细信息
     */
    @SaCheckPermission("wms:productionDailyReport:query")
    @GetMapping("/{id}")
    public R<ProductionDailyReportVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(productionDailyReportService.queryById(id));
    }

    /**
     * 新增生产日报
     */
    @SaCheckPermission("wms:productionDailyReport:add")
    @Log(title = "生产日报", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody ProductionDailyReportBo bo) {
        return R.ok(productionDailyReportService.insertByBo(bo));
    }

    /**
     * 修改生产日报
     */
    @SaCheckPermission("wms:productionDailyReport:edit")
    @Log(title = "生产日报", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProductionDailyReportBo bo) {
        productionDailyReportService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除生产日报
     */
    @SaCheckPermission("wms:productionDailyReport:remove")
    @Log(title = "生产日报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        productionDailyReportService.deleteById(id);
        return R.ok();
    }

}
