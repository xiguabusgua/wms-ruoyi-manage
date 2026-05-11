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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.wms.domain.bo.OeeRecordBo;
import com.ruoyi.wms.domain.vo.OeeRecordVo;
import com.ruoyi.wms.service.OeeRecordService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * OEE记录
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/oeeRecord")
public class OeeRecordController extends BaseController {

    private final OeeRecordService oeeRecordService;

    /**
     * 查询OEE记录列表
     */
    @SaCheckPermission("wms:oee:list")
    @GetMapping("/list")
    public TableDataInfo<OeeRecordVo> list(OeeRecordBo bo, PageQuery pageQuery) {
        return oeeRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出OEE记录列表
     */
    @SaCheckPermission("wms:oee:export")
    @Log(title = "OEE记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(OeeRecordBo bo, HttpServletResponse response) {
        List<OeeRecordVo> list = oeeRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "OEE记录", OeeRecordVo.class, response);
    }

    /**
     * 获取OEE记录详细信息
     */
    @SaCheckPermission("wms:oee:query")
    @GetMapping("/{id}")
    public R<OeeRecordVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(oeeRecordService.queryById(id));
    }

    /**
     * 新增OEE记录（自动计算OEE指标）
     */
    @SaCheckPermission("wms:oee:add")
    @Log(title = "OEE记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody OeeRecordBo bo) {
        oeeRecordService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改OEE记录
     */
    @SaCheckPermission("wms:oee:edit")
    @Log(title = "OEE记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody OeeRecordBo bo) {
        oeeRecordService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 查询设备在指定日期范围内的平均OEE
     */
    @SaCheckPermission("wms:oee:query")
    @GetMapping("/avgOee")
    public R<BigDecimal> avgOee(@RequestParam Long equipmentId,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return R.ok(oeeRecordService.avgOeeByDateRange(equipmentId, startDate, endDate));
    }

    /**
     * 删除OEE记录
     */
    @SaCheckPermission("wms:oee:remove")
    @Log(title = "OEE记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        oeeRecordService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
