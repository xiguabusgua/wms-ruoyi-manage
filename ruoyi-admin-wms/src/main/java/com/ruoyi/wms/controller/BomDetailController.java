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
import com.ruoyi.wms.domain.bo.BomDetailBo;
import com.ruoyi.wms.domain.vo.BomDetailVo;
import com.ruoyi.wms.service.BomDetailService;

import java.util.List;

/**
 * BOM明细
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/bomDetail")
public class BomDetailController extends BaseController {

    private final BomDetailService bomDetailService;

    /**
     * 查询BOM明细列表
     */
    @SaCheckPermission("wms:bom:list")
    @GetMapping("/list")
    public TableDataInfo<BomDetailVo> list(BomDetailBo bo, PageQuery pageQuery) {
        return bomDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取BOM明细详情
     */
    @SaCheckPermission("wms:bom:query")
    @GetMapping("/{id}")
    public R<BomDetailVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(bomDetailService.queryById(id));
    }

    /**
     * 新增BOM明细
     */
    @SaCheckPermission("wms:bom:add")
    @Log(title = "BOM明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BomDetailBo bo) {
        bomDetailService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改BOM明细
     */
    @SaCheckPermission("wms:bom:edit")
    @Log(title = "BOM明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BomDetailBo bo) {
        bomDetailService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除BOM明细
     */
    @SaCheckPermission("wms:bom:remove")
    @Log(title = "BOM明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        bomDetailService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
