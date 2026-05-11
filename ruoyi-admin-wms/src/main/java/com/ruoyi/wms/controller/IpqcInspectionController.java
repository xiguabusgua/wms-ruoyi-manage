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
import com.ruoyi.wms.domain.bo.IpqcInspectionBo;
import com.ruoyi.wms.domain.vo.IpqcInspectionVo;
import com.ruoyi.wms.service.IpqcInspectionService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/ipqcInspection")
public class IpqcInspectionController extends BaseController {

    private final IpqcInspectionService ipqcInspectionService;

    @SaCheckPermission("wms:ipqc:list")
    @GetMapping("/list")
    public TableDataInfo<IpqcInspectionVo> list(IpqcInspectionBo bo, PageQuery pageQuery) {
        return ipqcInspectionService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:ipqc:export")
    @Log(title = "过程检验", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IpqcInspectionBo bo, HttpServletResponse response) {
        List<IpqcInspectionVo> list = ipqcInspectionService.queryList(bo);
        ExcelUtil.exportExcel(list, "过程检验记录", IpqcInspectionVo.class, response);
    }

    @SaCheckPermission("wms:ipqc:query")
    @GetMapping("/{id}")
    public R<IpqcInspectionVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(ipqcInspectionService.queryById(id));
    }

    @SaCheckPermission("wms:ipqc:add")
    @Log(title = "过程检验", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody IpqcInspectionBo bo) {
        return R.ok(ipqcInspectionService.insertByBo(bo));
    }

    @SaCheckPermission("wms:ipqc:edit")
    @Log(title = "过程检验", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IpqcInspectionBo bo) {
        ipqcInspectionService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:ipqc:remove")
    @Log(title = "过程检验", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable List<Long> ids) {
        ipqcInspectionService.deleteByIds(ids);
        return R.ok();
    }

}
