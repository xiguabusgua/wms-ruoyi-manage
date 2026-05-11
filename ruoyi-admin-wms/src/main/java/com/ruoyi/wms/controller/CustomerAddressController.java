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
import com.ruoyi.wms.domain.bo.CustomerAddressBo;
import com.ruoyi.wms.domain.vo.CustomerAddressVo;
import com.ruoyi.wms.service.CustomerAddressService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户地址
 *
 * @author zcc
 * @date 2024-07-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/customerAddress")
public class CustomerAddressController extends BaseController {

    private final CustomerAddressService customerAddressService;

    /**
     * 查询客户地址列表（分页）
     */
    @SaCheckPermission("wms:customer:list")
    @GetMapping("/list")
    public TableDataInfo<CustomerAddressVo> list(CustomerAddressBo bo, PageQuery pageQuery) {
        return customerAddressService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取客户地址详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:customer:list")
    @GetMapping("/{id}")
    public R<CustomerAddressVo> getInfo(@NotNull(message = "主键不能为空")
                                           @PathVariable Long id) {
        return R.ok(customerAddressService.queryById(id));
    }

    /**
     * 新增客户地址
     */
    @SaCheckPermission("wms:customer:add")
    @Log(title = "客户地址", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CustomerAddressBo bo) {
        customerAddressService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改客户地址
     */
    @SaCheckPermission("wms:customer:edit")
    @Log(title = "客户地址", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CustomerAddressBo bo) {
        customerAddressService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除客户地址
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:customer:remove")
    @Log(title = "客户地址", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        customerAddressService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
