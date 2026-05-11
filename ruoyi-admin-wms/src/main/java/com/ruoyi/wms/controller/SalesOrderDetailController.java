package com.ruoyi.wms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.idempotent.annotation.RepeatSubmit;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.web.core.BaseController;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.wms.domain.bo.SalesOrderDetailBo;
import com.ruoyi.wms.domain.vo.SalesOrderDetailVo;
import com.ruoyi.wms.service.SalesOrderDetailService;

import java.util.Arrays;

/**
 * 销售订单明细
 *
 * @author zcc
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/salesOrderDetail")
public class SalesOrderDetailController extends BaseController {

    private final SalesOrderDetailService salesOrderDetailService;

    /**
     * 查询销售订单明细列表
     */
    @SaCheckPermission("wms:salesOrder:list")
    @GetMapping("/list")
    public TableDataInfo<SalesOrderDetailVo> list(SalesOrderDetailBo bo, PageQuery pageQuery) {
        return salesOrderDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取销售订单明细详情
     */
    @SaCheckPermission("wms:salesOrder:query")
    @GetMapping("/{id}")
    public R<SalesOrderDetailVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(salesOrderDetailService.queryById(id));
    }

    /**
     * 新增销售订单明细
     */
    @SaCheckPermission("wms:salesOrder:add")
    @Log(title = "销售订单明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SalesOrderDetailBo bo) {
        salesOrderDetailService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改销售订单明细
     */
    @SaCheckPermission("wms:salesOrder:edit")
    @Log(title = "销售订单明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SalesOrderDetailBo bo) {
        salesOrderDetailService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除销售订单明细
     */
    @SaCheckPermission("wms:salesOrder:remove")
    @Log(title = "销售订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long[] ids) {
        salesOrderDetailService.deleteByIds(Arrays.asList(ids));
        return R.ok();
    }

}
