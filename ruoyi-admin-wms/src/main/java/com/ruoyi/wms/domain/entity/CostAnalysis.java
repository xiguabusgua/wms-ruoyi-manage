package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 成本分析对象 wms_cost_analysis
 *
 * @author zcc
 * @date 2024-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_cost_analysis")
public class CostAnalysis extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分析编号
     */
    private String analysisNo;

    /**
     * 物料ID
     */
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
