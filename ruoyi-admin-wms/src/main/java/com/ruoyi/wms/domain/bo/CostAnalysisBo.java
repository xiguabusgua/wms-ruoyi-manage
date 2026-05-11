package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.CostAnalysis;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 成本分析业务对象 wms_cost_analysis
 *
 * @author zcc
 * @date 2024-08-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CostAnalysis.class, reverseConvertGenerate = false)
public class CostAnalysisBo extends BaseBo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 分析编号
     */
    @NotBlank(message = "分析编号不能为空", groups = { AddGroup.class })
    private String analysisNo;

    /**
     * 物料ID
     */
    @NotNull(message = "物料ID不能为空", groups = { AddGroup.class })
    private Long productId;

    /**
     * 分析日期
     */
    private LocalDate analysisDate;

    /**
     * 材料成本
     */
    private BigDecimal materialCost;

    /**
     * 人工成本
     */
    private BigDecimal laborCost;

    /**
     * 制造费用
     */
    private BigDecimal manufacturingCost;

    /**
     * 其他费用
     */
    private BigDecimal otherCost;

    /**
     * 总成本
     */
    private BigDecimal totalCost;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 产量
     */
    private Integer productionQuantity;

    /**
     * BOM版本
     */
    private String bomVersion;

    /**
     * 工艺路线
     */
    private String processRoute;

    /**
     * 分析周期(1月度2季度3年度)
     */
    private Integer analysisPeriod;

    /**
     * 备注
     */
    private String remark;
}
