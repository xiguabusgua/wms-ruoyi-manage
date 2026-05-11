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
import com.ruoyi.wms.domain.bo.WorkReportBo;
import com.ruoyi.wms.domain.vo.WorkReportVo;
import com.ruoyi.wms.service.WorkReportService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报工记录
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/workReport")
public class WorkReportController extends BaseController {

    private final WorkReportService workReportService;

    /**
     * 查询报工记录列表
     */
    @SaCheckPermission("wms:workReport:list")
    @GetMapping("/list")
    public TableDataInfo<WorkReportVo> list(WorkReportBo bo, PageQuery pageQuery) {
        return workReportService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出报工记录列表
     */
    @SaCheckPermission("wms:workReport:export")
    @Log(title = "报工记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(WorkReportBo bo, HttpServletResponse response) {
        List<WorkReportVo> list = workReportService.queryList(bo);
        ExcelUtil.exportExcel(list, "报工记录", WorkReportVo.class, response);
    }

    /**
     * 获取报工记录详细信息
     */
    @SaCheckPermission("wms:workReport:query")
    @GetMapping("/{id}")
    public R<WorkReportVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(workReportService.queryById(id));
    }

    /**
     * 新增报工记录
     */
    @SaCheckPermission("wms:workReport:add")
    @Log(title = "报工记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody WorkReportBo bo) {
        return R.ok(workReportService.insertByBo(bo));
    }

    /**
     * 开工报工
     */
    @SaCheckPermission("wms:workReport:add")
    @Log(title = "报工记录-开工", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/startReport")
    public R<Void> startReport(@RequestParam Long workOrderId,
                               @RequestParam String operator,
                               @RequestParam(required = false) String remark) {
        workReportService.startReport(workOrderId, operator, remark);
        return R.ok();
    }

    /**
     * 完工报工
     */
    @SaCheckPermission("wms:workReport:add")
    @Log(title = "报工记录-完工", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/completeReport")
    public R<Void> completeReport(@RequestParam Long workOrderId,
                                  @RequestParam BigDecimal quantity,
                                  @RequestParam(required = false) BigDecimal defectiveQuantity,
                                  @RequestParam String operator,
                                  @RequestParam(required = false) String remark) {
        workReportService.completeReport(workOrderId, quantity, defectiveQuantity, operator, remark);
        return R.ok();
    }

    /**
     * 修改报工记录
     */
    @SaCheckPermission("wms:workReport:edit")
    @Log(title = "报工记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WorkReportBo bo) {
        workReportService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除报工记录
     */
    @SaCheckPermission("wms:workReport:remove")
    @Log(title = "报工记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        workReportService.deleteById(id);
        return R.ok();
    }

}
