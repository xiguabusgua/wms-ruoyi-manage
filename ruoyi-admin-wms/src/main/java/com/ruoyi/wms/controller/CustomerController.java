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
import com.ruoyi.wms.domain.bo.CustomerBo;
import com.ruoyi.wms.domain.vo.CustomerVo;
import com.ruoyi.wms.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户管理
 *
 * @author zcc
 * @date 2024-07-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/customer")
public class CustomerController extends BaseController {

    private final CustomerService customerService;

    /**
     * 查询客户列表（分页）
     */
    @SaCheckPermission("wms:customer:list")
    @GetMapping("/list")
    public TableDataInfo<CustomerVo> list(CustomerBo bo, PageQuery pageQuery) {
        return customerService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询客户列表（无分页）
     */
    @SaCheckPermission("wms:customer:list")
    @GetMapping("/listNoPage")
    public R<List<CustomerVo>> listNoPage(CustomerBo bo) {
        return R.ok(customerService.queryList(bo));
    }

    /**
     * 导出客户列表
     */
    @SaCheckPermission("wms:customer:list")
    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CustomerBo bo, HttpServletResponse response) {
        List<CustomerVo> list = customerService.queryList(bo);
        ExcelUtil.exportExcel(list, "客户数据", CustomerVo.class, response);
    }

    /**
     * 获取客户详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:customer:list")
    @GetMapping("/{id}")
    public R<CustomerVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(customerService.queryById(id));
    }

    /**
     * 新增客户
     */
    @SaCheckPermission("wms:customer:add")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CustomerBo bo) {
        customerService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改客户
     */
    @SaCheckPermission("wms:customer:edit")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CustomerBo bo) {
        customerService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除客户
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:customer:remove")
    @Log(title = "客户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        customerService.deleteById(id);
        return R.ok();
    }
}
