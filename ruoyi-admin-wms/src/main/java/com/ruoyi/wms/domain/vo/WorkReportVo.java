package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.WorkReport;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 报工记录视图对象 wms_work_report
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = WorkReport.class)
public class WorkReportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "工单ID")
    private Long workOrderId;

    @ExcelProperty(value = "工单号")
    private String workOrderNo;

    @ExcelProperty(value = "报工类型")
    private Integer reportType;

    @ExcelProperty(value = "报工类型名称")
    private String reportTypeName;

    @ExcelProperty(value = "报工时间")
    private LocalDateTime reportTime;

    @ExcelProperty(value = "数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "不良数量")
    private BigDecimal defectiveQuantity;

    @ExcelProperty(value = "操作人")
    private String operator;

    @ExcelProperty(value = "备注")
    private String remark;

}
