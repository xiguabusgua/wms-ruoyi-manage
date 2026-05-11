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
import com.ruoyi.wms.domain.bo.MoldLifeRecordBo;
import com.ruoyi.wms.domain.vo.MoldLifeRecordVo;
import com.ruoyi.wms.service.MoldLifeRecordService;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/moldLifeRecord")
public class MoldLifeRecordController extends BaseController {

    private final MoldLifeRecordService moldLifeRecordService;

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/list")
    public TableDataInfo<MoldLifeRecordVo> list(MoldLifeRecordBo bo, PageQuery pageQuery) {
        return moldLifeRecordService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("wms:mold:list")
    @GetMapping("/listByMold/{moldId}")
    public R<List<MoldLifeRecordVo>> listByMold(@PathVariable Long moldId) {
        return R.ok(moldLifeRecordService.queryByMoldId(moldId));
    }

    @SaCheckPermission("wms:mold:export")
    @Log(title = "模具冲次记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MoldLifeRecordBo bo, HttpServletResponse response) {
        List<MoldLifeRecordVo> list = moldLifeRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "模具冲次记录", MoldLifeRecordVo.class, response);
    }

    @SaCheckPermission("wms:mold:query")
    @GetMapping("/{id}")
    public R<MoldLifeRecordVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(moldLifeRecordService.queryById(id));
    }

    @SaCheckPermission("wms:mold:add")
    @Log(title = "模具冲次记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MoldLifeRecordBo bo) {
        moldLifeRecordService.insertByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:edit")
    @Log(title = "模具冲次记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MoldLifeRecordBo bo) {
        moldLifeRecordService.updateByBo(bo);
        return R.ok();
    }

    @SaCheckPermission("wms:mold:remove")
    @Log(title = "模具冲次记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        moldLifeRecordService.deleteById(id);
        return R.ok();
    }

}
