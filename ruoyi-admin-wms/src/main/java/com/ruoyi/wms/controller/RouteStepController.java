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
import com.ruoyi.wms.domain.bo.RouteStepBo;
import com.ruoyi.wms.domain.vo.RouteStepVo;
import com.ruoyi.wms.service.RouteStepService;

import java.util.List;

/**
 * 工序步骤
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/routeStep")
public class RouteStepController extends BaseController {

    private final RouteStepService routeStepService;

    /**
     * 查询工序步骤列表
     */
    @SaCheckPermission("wms:process:list")
    @GetMapping("/list")
    public TableDataInfo<RouteStepVo> list(RouteStepBo bo, PageQuery pageQuery) {
        return routeStepService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取工序步骤详情
     */
    @SaCheckPermission("wms:process:query")
    @GetMapping("/{id}")
    public R<RouteStepVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(routeStepService.queryById(id));
    }

    /**
     * 新增工序步骤
     */
    @SaCheckPermission("wms:process:add")
    @Log(title = "工序步骤", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody RouteStepBo bo) {
        routeStepService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改工序步骤
     */
    @SaCheckPermission("wms:process:edit")
    @Log(title = "工序步骤", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody RouteStepBo bo) {
        routeStepService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除工序步骤
     */
    @SaCheckPermission("wms:process:remove")
    @Log(title = "工序步骤", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        routeStepService.deleteByIds(java.util.Arrays.asList(ids));
        return R.ok();
    }

}
