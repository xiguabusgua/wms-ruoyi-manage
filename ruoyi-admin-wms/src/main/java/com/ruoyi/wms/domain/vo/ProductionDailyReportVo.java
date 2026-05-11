package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.ProductionDailyReport;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 生产日报视图对象 wms_production_daily_report
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = ProductionDailyReport.class)
public class ProductionDailyReportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "报告日期")
    private LocalDate reportDate;

    @ExcelProperty(value = "工单ID")
    private Long workOrderId;

    @ExcelProperty(value = "工单号")
    private String workOrderNo;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "物料名称")
    private String productName;

    @ExcelProperty(value = "物料编码")
    private String productCode;

    @ExcelProperty(value = "计划数量")
    private BigDecimal planQuantity;

    @ExcelProperty(value = "完工数量")
    private BigDecimal completedQuantity;

    @ExcelProperty(value = "不良数量")
    private BigDecimal defectiveQuantity;

    @ExcelProperty(value = "良率(%)")
    private BigDecimal yieldRate;

    @ExcelProperty(value = "操作人")
    private String operator;

    @ExcelProperty(value = "备注")
    private String remark;

}
