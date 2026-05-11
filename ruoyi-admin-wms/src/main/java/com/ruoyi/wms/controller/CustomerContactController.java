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
import com.ruoyi.wms.domain.bo.CustomerContactBo;
import com.ruoyi.wms.domain.vo.CustomerContactVo;
import com.ruoyi.wms.service.CustomerContactService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户联系人
 *
 * @author zcc
 * @date 2024-07-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/customerContact")
public class CustomerContactController extends BaseController {

    private final CustomerContactService customerContactService;

    /**
     * 查询客户联系人列表（分页）
     */
    @SaCheckPermission("wms:customer:list")
    @GetMapping("/list")
    public TableDataInfo<CustomerContactVo> list(CustomerContactBo bo, PageQuery pageQuery) {
        return customerContactService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取客户联系人详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:customer:list")
    @GetMapping("/{id}")
    public R<CustomerContactVo> getInfo(@NotNull(message = "主键不能为空")
                                           @PathVariable Long id) {
        return R.ok(customerContactService.queryById(id));
    }

    /**
     * 新增客户联系人
     */
    @SaCheckPermission("wms:customer:add")
    @Log(title = "客户联系人", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CustomerContactBo bo) {
        customerContactService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改客户联系人
     */
    @SaCheckPermission("wms:customer:edit")
    @Log(title = "客户联系人", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CustomerContactBo bo) {
        customerContactService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除客户联系人
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:customer:remove")
    @Log(title = "客户联系人", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        customerContactService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
