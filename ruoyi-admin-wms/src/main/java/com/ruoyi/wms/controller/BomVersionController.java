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
import com.ruoyi.wms.domain.bo.BomVersionBo;
import com.ruoyi.wms.domain.vo.BomVersionVo;
import com.ruoyi.wms.service.BomVersionService;

import java.util.List;

/**
 * BOM版本记录
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/bomVersion")
public class BomVersionController extends BaseController {

    private final BomVersionService bomVersionService;

    /**
     * 查询BOM版本记录列表
     */
    @SaCheckPermission("wms:bom:list")
    @GetMapping("/list")
    public TableDataInfo<BomVersionVo> list(BomVersionBo bo, PageQuery pageQuery) {
        return bomVersionService.queryPageList(bo, pageQuery);
    }

    /**
     * 根据BOM主表ID查询所有版本记录
     */
    @SaCheckPermission("wms:bom:query")
    @GetMapping("/byBomId/{bomId}")
    public R<List<BomVersionVo>> getByBomId(@PathVariable Long bomId) {
        return R.ok(bomVersionService.queryByBomId(bomId));
    }

    /**
     * 导出BOM版本记录列表
     */
    @SaCheckPermission("wms:bom:export")
    @Log(title = "BOM版本记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BomVersionBo bo, HttpServletResponse response) {
        List<BomVersionVo> list = bomVersionService.queryList(bo);
        ExcelUtil.exportExcel(list, "BOM版本记录", BomVersionVo.class, response);
    }

    /**
     * 获取BOM版本记录详情
     */
    @SaCheckPermission("wms:bom:query")
    @GetMapping("/{id}")
    public R<BomVersionVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(bomVersionService.queryById(id));
    }

    /**
     * 新增BOM版本记录
     */
    @SaCheckPermission("wms:bom:add")
    @Log(title = "BOM版本记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BomVersionBo bo) {
        bomVersionService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 删除BOM版本记录
     */
    @SaCheckPermission("wms:bom:remove")
    @Log(title = "BOM版本记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        bomVersionService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
