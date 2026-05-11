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
import com.ruoyi.wms.domain.bo.PickingTaskBo;
import com.ruoyi.wms.domain.vo.PickingTaskVo;
import com.ruoyi.wms.service.PickingTaskService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 拣货任务
 *
 * @author zcc
 * @date 2024-08-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/pickingTask")
public class PickingTaskController extends BaseController {

    private final PickingTaskService pickingTaskService;

    /**
     * 查询拣货任务列表
     */
    @SaCheckPermission("wms:picking:all")
    @GetMapping("/list")
    public TableDataInfo<PickingTaskVo> list(PickingTaskBo bo, PageQuery pageQuery) {
        return pickingTaskService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出拣货任务列表
     */
    @SaCheckPermission("wms:picking:all")
    @Log(title = "拣货任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PickingTaskBo bo, HttpServletResponse response) {
        List<PickingTaskVo> list = pickingTaskService.queryList(bo);
        ExcelUtil.exportExcel(list, "拣货任务", PickingTaskVo.class, response);
    }

    /**
     * 获取拣货任务详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:picking:all")
    @GetMapping("/{id}")
    public R<PickingTaskVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(pickingTaskService.queryById(id));
    }

    /**
     * 新增拣货任务
     */
    @SaCheckPermission("wms:picking:all")
    @Log(title = "拣货任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PickingTaskBo bo) {
        pickingTaskService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改拣货任务
     */
    @SaCheckPermission("wms:picking:all")
    @Log(title = "拣货任务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PickingTaskBo bo) {
        pickingTaskService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除拣货任务
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:picking:all")
    @Log(title = "拣货任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        pickingTaskService.deleteById(id);
        return R.ok();
    }
}
