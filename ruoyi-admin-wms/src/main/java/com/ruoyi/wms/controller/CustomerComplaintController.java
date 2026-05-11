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
import com.ruoyi.wms.domain.bo.CustomerComplaintBo;
import com.ruoyi.wms.domain.vo.CustomerComplaintVo;
import com.ruoyi.wms.service.CustomerComplaintService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/customerComplaint")
public class CustomerComplaintController extends BaseController {

    private final CustomerComplaintService customerComplaintService;

    @SaCheckPermission("wms:complaint:list")
    @GetMapping("/list")
    public TableDataInfo<CustomerComplaintVo> list(CustomerComplaintBo bo, PageQuery pageQuery) {
        return customerComplaintService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:complaint:export")
    @Log(title = "客户投诉", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CustomerComplaintBo bo, HttpServletResponse response) {
        List<CustomerComplaintVo> list = customerComplaintService.queryList(bo);
        ExcelUtil.exportExcel(list, "客户投诉记录", CustomerComplaintVo.class, response);
    }

    @SaCheckPermission("wms:complaint:query")
    @GetMapping("/{id}")
    public R<CustomerComplaintVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(customerComplaintService.queryById(id));
    }

    @SaCheckPermission("wms:complaint:add")
    @Log(title = "客户投诉", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody CustomerComplaintBo bo) {
        return R.ok(customerComplaintService.insertByBo(bo));
    }

    @SaCheckPermission("wms:complaint:edit")
    @Log(title = "客户投诉", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CustomerComplaintBo bo) {
        customerComplaintService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:complaint:remove")
    @Log(title = "客户投诉", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable List<Long> ids) {
        customerComplaintService.deleteByIds(ids);
        return R.ok();
    }

}
