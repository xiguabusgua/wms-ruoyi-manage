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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.wms.domain.bo.MoldCategoryBo;
import com.ruoyi.wms.domain.vo.MoldCategoryVo;
import com.ruoyi.wms.service.MoldCategoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/moldCategory")
public class MoldCategoryController extends BaseController {

    private final MoldCategoryService moldCategoryService;

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/list")
    public TableDataInfo<MoldCategoryVo> list(MoldCategoryBo bo, PageQuery pageQuery) {
        return moldCategoryService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/listNoPage")
    public R<List<MoldCategoryVo>> listNoPage(MoldCategoryBo bo) {
        return R.ok(moldCategoryService.queryList(bo));
    }

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/treeselect")
    public R<List<MoldCategoryVo>> treeselect(MoldCategoryBo query) {
        List<MoldCategoryVo> categories = moldCategoryService.queryList(query);
        return R.ok(moldCategoryService.buildTree(categories));
    }

    @SaCheckPermission("wms:mold:export")
    @Log(title = "模具分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MoldCategoryBo bo, HttpServletResponse response) {
        List<MoldCategoryVo> list = moldCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "模具分类", MoldCategoryVo.class, response);
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/{id}")
    public R<MoldCategoryVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(moldCategoryService.queryById(id));
    }

    @SaCheckPermission("wms:mold:add")
    @Log(title = "模具分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MoldCategoryBo bo) {
        moldCategoryService.insertByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MoldCategoryBo bo) {
        moldCategoryService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:remove")
    @Log(title = "模具分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        List<Long> idList = new ArrayList<>(Arrays.asList(ids));
        moldCategoryService.deleteByIds(idList);
        return R.ok();
    }

}
