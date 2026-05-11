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
import com.ruoyi.wms.domain.bo.BomMainBo;
import com.ruoyi.wms.domain.vo.BomMainVo;
import com.ruoyi.wms.service.BomMainService;

import java.util.List;

/**
 * BOM主表
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/bomMain")
public class BomMainController extends BaseController {

    private final BomMainService bomMainService;

    /**
     * 查询BOM主表列表
     */
    @SaCheckPermission("wms:bom:list")
    @GetMapping("/list")
    public TableDataInfo<BomMainVo> list(BomMainBo bo, PageQuery pageQuery) {
        return bomMainService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出BOM主表列表
     */
    @SaCheckPermission("wms:bom:export")
    @Log(title = "BOM主表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BomMainBo bo, HttpServletResponse response) {
        List<BomMainVo> list = bomMainService.queryList(bo);
        ExcelUtil.exportExcel(list, "BOM主表", BomMainVo.class, response);
    }

    /**
     * 获取BOM主表详细信息
     */
    @SaCheckPermission("wms:bom:query")
    @GetMapping("/{id}")
    public R<BomMainVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(bomMainService.queryById(id));
    }

    /**
     * 新增BOM主表（草稿）
     */
    @SaCheckPermission("wms:bom:add")
    @Log(title = "BOM主表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody BomMainBo bo) {
        Long id = bomMainService.insertByBo(bo);
        return R.ok(id);
    }

    /**
     * 发布BOM（草稿->正式）
     */
    @SaCheckPermission("wms:bom:edit")
    @Log(title = "BOM-发布", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/publish/{id}")
    public R<Void> publish(@PathVariable Long id) {
        bomMainService.publish(id);
        return R.ok();
    }

    /**
     * 使BOM失效
     */
    @SaCheckPermission("wms:bom:edit")
    @Log(title = "BOM-失效", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/expire/{id}")
    public R<Void> expire(@PathVariable Long id) {
        bomMainService.expire(id);
        return R.ok();
    }

    /**
     * 升级BOM版本
     */
    @SaCheckPermission("wms:bom:edit")
    @Log(title = "BOM-升级版本", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/upgradeVersion/{id}")
    public R<Void> upgradeVersion(@PathVariable Long id,
                                  @RequestParam String newVersion,
                                  @RequestParam(required = false) String changeDesc) {
        bomMainService.upgradeVersion(id, newVersion, changeDesc);
        return R.ok();
    }

    /**
     * 修改BOM主表
     */
    @SaCheckPermission("wms:bom:edit")
    @Log(title = "BOM主表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BomMainBo bo) {
        bomMainService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除BOM主表
     */
    @SaCheckPermission("wms:bom:remove")
    @Log(title = "BOM主表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        bomMainService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
