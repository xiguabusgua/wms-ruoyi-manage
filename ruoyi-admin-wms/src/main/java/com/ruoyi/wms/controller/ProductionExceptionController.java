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
import com.ruoyi.wms.domain.bo.ProductionExceptionBo;
import com.ruoyi.wms.domain.vo.ProductionExceptionVo;
import com.ruoyi.wms.service.ProductionExceptionService;

import java.util.List;

/**
 * 异常上报
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/productionException")
public class ProductionExceptionController extends BaseController {

    private final ProductionExceptionService productionExceptionService;

    /**
     * 查询异常上报列表
     */
    @SaCheckPermission("wms:productionException:list")
    @GetMapping("/list")
    public TableDataInfo<ProductionExceptionVo> list(ProductionExceptionBo bo, PageQuery pageQuery) {
        return productionExceptionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常上报列表
     */
    @SaCheckPermission("wms:productionException:export")
    @Log(title = "异常上报", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProductionExceptionBo bo, HttpServletResponse response) {
        List<ProductionExceptionVo> list = productionExceptionService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常上报", ProductionExceptionVo.class, response);
    }

    /**
     * 获取异常上报详细信息
     */
    @SaCheckPermission("wms:productionException:query")
    @GetMapping("/{id}")
    public R<ProductionExceptionVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(productionExceptionService.queryById(id));
    }

    /**
     * 新增异常上报
     */
    @SaCheckPermission("wms:productionException:add")
    @Log(title = "异常上报", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody ProductionExceptionBo bo) {
        return R.ok(productionExceptionService.insertByBo(bo));
    }

    /**
     * 开始处理 - 未处理→处理中
     */
    @SaCheckPermission("wms:productionException:edit")
    @Log(title = "异常上报-开始处理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/startHandle/{id}")
    public R<Void> startHandle(@PathVariable Long id) {
        productionExceptionService.startHandle(id);
        return R.ok();
    }

    /**
     * 解决 - 处理中→已解决
     */
    @SaCheckPermission("wms:productionException:edit")
    @Log(title = "异常上报-已解决", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/resolve/{id}")
    public R<Void> resolve(@PathVariable Long id,
                           @RequestParam(required = false) String handleResult) {
        productionExceptionService.resolve(id, handleResult);
        return R.ok();
    }

    /**
     * 取消处理 - 处理中→未处理
     */
    @SaCheckPermission("wms:productionException:edit")
    @Log(title = "异常上报-取消处理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/cancelHandle/{id}")
    public R<Void> cancelHandle(@PathVariable Long id) {
        productionExceptionService.cancelHandle(id);
        return R.ok();
    }

    /**
     * 修改异常上报
     */
    @SaCheckPermission("wms:productionException:edit")
    @Log(title = "异常上报", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProductionExceptionBo bo) {
        productionExceptionService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除异常上报
     */
    @SaCheckPermission("wms:productionException:remove")
    @Log(title = "异常上报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        productionExceptionService.deleteById(id);
        return R.ok();
    }

}
