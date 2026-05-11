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
import com.ruoyi.wms.domain.bo.WorkOrderBo;
import com.ruoyi.wms.domain.vo.WorkOrderVo;
import com.ruoyi.wms.service.WorkOrderService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 工单/派工单
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/workOrder")
public class WorkOrderController extends BaseController {

    private final WorkOrderService workOrderService;

    /**
     * 查询工单列表
     */
    @SaCheckPermission("wms:workOrder:list")
    @GetMapping("/list")
    public TableDataInfo<WorkOrderVo> list(WorkOrderBo bo, PageQuery pageQuery) {
        return workOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出工单列表
     */
    @SaCheckPermission("wms:workOrder:export")
    @Log(title = "工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(WorkOrderBo bo, HttpServletResponse response) {
        List<WorkOrderVo> list = workOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "工单", WorkOrderVo.class, response);
    }

    /**
     * 获取工单详细信息
     */
    @SaCheckPermission("wms:workOrder:query")
    @GetMapping("/{id}")
    public R<WorkOrderVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(workOrderService.queryById(id));
    }

    /**
     * 新增工单
     */
    @SaCheckPermission("wms:workOrder:add")
    @Log(title = "工单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody WorkOrderBo bo) {
        return R.ok(workOrderService.insertByBo(bo));
    }

    /**
     * 从生产计划生成工单
     */
    @SaCheckPermission("wms:workOrder:add")
    @Log(title = "工单-从计划生成", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/generateFromPlan")
    public R<Long> generateFromPlan(@RequestParam Long planId,
                                    @RequestParam Long productId,
                                    @RequestParam BigDecimal plannedQuantity,
                                    @RequestParam(required = false) Long equipmentId,
                                    @RequestParam(required = false) Long moldId,
                                    @RequestParam(required = false) String operator) {
        return R.ok(workOrderService.generateFromPlan(planId, productId, plannedQuantity, equipmentId, moldId, operator));
    }

    /**
     * 分配机台和模具
     */
    @SaCheckPermission("wms:workOrder:edit")
    @Log(title = "工单-分配机台模具", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/assignEquipment/{id}")
    public R<Void> assignEquipment(@PathVariable Long id,
                                   @RequestParam Long equipmentId,
                                   @RequestParam(required = false) Long moldId) {
        workOrderService.assignEquipment(id, equipmentId, moldId);
        return R.ok();
    }

    /**
     * 开工 - 待生产/暂停→生产中
     */
    @SaCheckPermission("wms:workOrder:edit")
    @Log(title = "工单-开工", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/startWork/{id}")
    public R<Void> startWork(@PathVariable Long id) {
        workOrderService.startWork(id);
        return R.ok();
    }

    /**
     * 暂停 - 生产中→暂停
     */
    @SaCheckPermission("wms:workOrder:edit")
    @Log(title = "工单-暂停", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/pause/{id}")
    public R<Void> pause(@PathVariable Long id) {
        workOrderService.pause(id);
        return R.ok();
    }

    /**
     * 恢复 - 暂停→生产中
     */
    @SaCheckPermission("wms:workOrder:edit")
    @Log(title = "工单-恢复", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/resume/{id}")
    public R<Void> resume(@PathVariable Long id) {
        workOrderService.resume(id);
        return R.ok();
    }

    /**
     * 完工 - 生产中/暂停→已完成
     */
    @SaCheckPermission("wms:workOrder:edit")
    @Log(title = "工单-完工", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/complete/{id}")
    public R<Void> complete(@PathVariable Long id) {
        workOrderService.complete(id);
        return R.ok();
    }

    /**
     * 取消工单
     */
    @SaCheckPermission("wms:workOrder:edit")
    @Log(title = "工单-取消", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/cancel/{id}")
    public R<Void> cancel(@PathVariable Long id) {
        workOrderService.cancel(id);
        return R.ok();
    }

    /**
     * 修改工单
     */
    @SaCheckPermission("wms:workOrder:edit")
    @Log(title = "工单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WorkOrderBo bo) {
        workOrderService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除工单
     */
    @SaCheckPermission("wms:workOrder:remove")
    @Log(title = "工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        workOrderService.deleteById(id);
        return R.ok();
    }

}
