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
import com.ruoyi.wms.domain.bo.CostAnalysisBo;
import com.ruoyi.wms.domain.vo.CostAnalysisVo;
import com.ruoyi.wms.service.CostAnalysisService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成本分析
 *
 * @author zcc
 * @date 2024-08-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/costAnalysis")
public class CostAnalysisController extends BaseController {

    private final CostAnalysisService costAnalysisService;

    /**
     * 查询成本分析列表
     */
    @SaCheckPermission("wms:cost:all")
    @GetMapping("/list")
    public TableDataInfo<CostAnalysisVo> list(CostAnalysisBo bo, PageQuery pageQuery) {
        return costAnalysisService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出成本分析列表
     */
    @SaCheckPermission("wms:cost:all")
    @Log(title = "成本分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CostAnalysisBo bo, HttpServletResponse response) {
        List<CostAnalysisVo> list = costAnalysisService.queryList(bo);
        ExcelUtil.exportExcel(list, "成本分析", CostAnalysisVo.class, response);
    }

    /**
     * 获取成本分析详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:cost:all")
    @GetMapping("/{id}")
    public R<CostAnalysisVo> getInfo(@NotNull(message = "主键不能为空")
                                      @PathVariable Long id) {
        return R.ok(costAnalysisService.queryById(id));
    }

    /**
     * 新增成本分析
     */
    @SaCheckPermission("wms:cost:all")
    @Log(title = "成本分析", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CostAnalysisBo bo) {
        costAnalysisService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改成本分析
     */
    @SaCheckPermission("wms:cost:all")
    @Log(title = "成本分析", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CostAnalysisBo bo) {
        costAnalysisService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除成本分析
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:cost:all")
    @Log(title = "成本分析", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        costAnalysisService.deleteById(id);
        return R.ok();
    }
}
