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
import com.ruoyi.wms.domain.bo.ProcessRouteBo;
import com.ruoyi.wms.domain.vo.ProcessRouteVo;
import com.ruoyi.wms.service.ProcessRouteService;

import java.util.List;

/**
 * 工艺路线主表
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/processRoute")
public class ProcessRouteController extends BaseController {

    private final ProcessRouteService processRouteService;

    /**
     * 查询工艺路线列表
     */
    @SaCheckPermission("wms:process:list")
    @GetMapping("/list")
    public TableDataInfo<ProcessRouteVo> list(ProcessRouteBo bo, PageQuery pageQuery) {
        return processRouteService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出工艺路线列表
     */
    @SaCheckPermission("wms:process:export")
    @Log(title = "工艺路线", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProcessRouteBo bo, HttpServletResponse response) {
        List<ProcessRouteVo> list = processRouteService.queryList(bo);
        ExcelUtil.exportExcel(list, "工艺路线", ProcessRouteVo.class, response);
    }

    /**
     * 获取工艺路线详细信息
     */
    @SaCheckPermission("wms:process:query")
    @GetMapping("/{id}")
    public R<ProcessRouteVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(processRouteService.queryById(id));
    }

    /**
     * 新增工艺路线（草稿）
     */
    @SaCheckPermission("wms:process:add")
    @Log(title = "工艺路线", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody ProcessRouteBo bo) {
        Long id = processRouteService.insertByBo(bo);
        return R.ok(id);
    }

    /**
     * 启用工艺路线
     */
    @SaCheckPermission("wms:process:edit")
    @Log(title = "工艺路线-启用", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/enable/{id}")
    public R<Void> enable(@PathVariable Long id) {
        processRouteService.enable(id);
        return R.ok();
    }

    /**
     * 停用工艺路线
     */
    @SaCheckPermission("wms:process:edit")
    @Log(title = "工艺路线-停用", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/disable/{id}")
    public R<Void> disable(@PathVariable Long id) {
        processRouteService.disable(id);
        return R.ok();
    }

    /**
     * 修改工艺路线
     */
    @SaCheckPermission("wms:process:edit")
    @Log(title = "工艺路线", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProcessRouteBo bo) {
        processRouteService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除工艺路线
     */
    @SaCheckPermission("wms:process:remove")
    @Log(title = "工艺路线", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        processRouteService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
