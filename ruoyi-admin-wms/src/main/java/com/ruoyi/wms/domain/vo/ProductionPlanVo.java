package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.ProductionPlan;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 生产计划视图对象 wms_production_plan
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = ProductionPlan.class)
public class ProductionPlanVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "计划编号")
    private String planNo;

    @ExcelProperty(value = "销售订单ID")
    private Long salesOrderId;

    @ExcelProperty(value = "销售订单号")
    private String salesOrderNo;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "物料名称")
    private String productName;

    @ExcelProperty(value = "物料编码")
    private String productCode;

    @ExcelProperty(value = "计划数量")
    private BigDecimal planQuantity;

    @ExcelProperty(value = "已生产数量")
    private BigDecimal producedQuantity;

    @ExcelProperty(value = "计划开始日期")
    private LocalDate planStartDate;

    @ExcelProperty(value = "计划结束日期")
    private LocalDate planEndDate;

    @ExcelProperty(value = "优先级")
    private Integer priority;

    @ExcelProperty(value = "优先级名称")
    private String priorityName;

    @ExcelProperty(value = "计划状态")
    private Integer planStatus;

    @ExcelProperty(value = "计划状态名称")
    private String planStatusName;

    @ExcelProperty(value = "备注")
    private String remark;

}
