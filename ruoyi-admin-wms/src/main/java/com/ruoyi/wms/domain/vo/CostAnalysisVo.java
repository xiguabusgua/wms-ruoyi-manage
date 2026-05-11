package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.CostAnalysis;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 成本分析视图对象 wms_cost_analysis
 *
 * @author zcc
 * @date 2024-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = CostAnalysis.class)
public class CostAnalysisVo extends BaseVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 分析编号
     */
    @ExcelProperty(value = "分析编号")
    private String analysisNo;

    /**
     * 物料ID
     */
    @ExcelProperty(value = "物料ID")
    private Long productId;

    /**
     * 分析日期
     */
    @ExcelProperty(value = "分析日期")
    private LocalDate analysisDate;

    /**
     * 材料成本
     */
    @ExcelProperty(value = "材料成本")
    private BigDecimal materialCost;

    /**
     * 人工成本
     */
    @ExcelProperty(value = "人工成本")
    private BigDecimal laborCost;

    /**
     * 制造费用
     */
    @ExcelProperty(value = "制造费用")
    private BigDecimal manufacturingCost;

    /**
     * 其他费用
     */
    @ExcelProperty(value = "其他费用")
    private BigDecimal otherCost;

    /**
     * 总成本
     */
    @ExcelProperty(value = "总成本")
    private BigDecimal totalCost;

    /**
     * 单位成本
     */
    @ExcelProperty(value = "单位成本")
    private BigDecimal unitCost;

    /**
     * 产量
     */
    @ExcelProperty(value = "产量")
    private Integer productionQuantity;

    /**
     * BOM版本
     */
    @ExcelProperty(value = "BOM版本")
    private String bomVersion;

    /**
     * 工艺路线
     */
    @ExcelProperty(value = "工艺路线")
    private String processRoute;

    /**
     * 分析周期(1月度2季度3年度)
     */
    @ExcelProperty(value = "分析周期")
    private Integer analysisPeriod;

    /**
     * 分析周期名称
     */
    private String analysisPeriodName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
}
