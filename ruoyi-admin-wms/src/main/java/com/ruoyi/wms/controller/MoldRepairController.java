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
import com.ruoyi.wms.domain.bo.MoldRepairBo;
import com.ruoyi.wms.domain.vo.MoldRepairVo;
import com.ruoyi.wms.service.MoldRepairService;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/moldRepair")
public class MoldRepairController extends BaseController {

    private final MoldRepairService moldRepairService;

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/list")
    public TableDataInfo<MoldRepairVo> list(MoldRepairBo bo, PageQuery pageQuery) {
        return moldRepairService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/listByMold/{moldId}")
    public R<List<MoldRepairVo>> listByMold(@PathVariable Long moldId) {
        return R.ok(moldRepairService.queryByMoldId(moldId));
    }

    @SaCheckPermission("wms:mold:export")
    @Log(title = "模具维修记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MoldRepairBo bo, HttpServletResponse response) {
        List<MoldRepairVo> list = moldRepairService.queryList(bo);
        ExcelUtil.exportExcel(list, "模具维修记录", MoldRepairVo.class, response);
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/{id}")
    public R<MoldRepairVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(moldRepairService.queryById(id));
    }

    @SaCheckPermission("wms:mold:add")
    @Log(title = "模具维修记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MoldRepairBo bo) {
        moldRepairService.insertByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具维修记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MoldRepairBo bo) {
        moldRepairService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具维修-完成维修", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/complete/{id}")
    public R<Void> complete(@PathVariable Long id,
                            @RequestParam(required = false) String repairContent,
                            @RequestParam(required = false) String replacedParts,
                            @RequestParam(required = false) BigDecimal cost,
                            @RequestParam(required = false) String repairer) {
        moldRepairService.completeRepair(id, repairContent, replacedParts, cost, repairer);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:remove")
    @Log(title = "模具维修记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        moldRepairService.deleteById(id);
        return R.ok();
    }

}
