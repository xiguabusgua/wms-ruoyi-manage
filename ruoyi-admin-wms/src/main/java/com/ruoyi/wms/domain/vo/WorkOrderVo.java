package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.WorkOrder;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工单/派工单视图对象 wms_work_order
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = WorkOrder.class)
public class WorkOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "工单号")
    private String workOrderNo;

    @ExcelProperty(value = "生产计划ID")
    private Long planId;

    @ExcelProperty(value = "计划编号")
    private String planNo;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "物料名称")
    private String productName;

    @ExcelProperty(value = "物料编码")
    private String productCode;

    @ExcelProperty(value = "计划数量")
    private BigDecimal plannedQuantity;

    @ExcelProperty(value = "完工数量")
    private BigDecimal completedQuantity;

    @ExcelProperty(value = "不良数量")
    private BigDecimal defectiveQuantity;

    @ExcelProperty(value = "设备ID")
    private Long equipmentId;

    @ExcelProperty(value = "设备名称")
    private String equipmentName;

    @ExcelProperty(value = "模具ID")
    private Long moldId;

    @ExcelProperty(value = "模具名称")
    private String moldName;

    @ExcelProperty(value = "工单状态")
    private Integer workStatus;

    @ExcelProperty(value = "工单状态名称")
    private String workStatusName;

    @ExcelProperty(value = "实际开始时间")
    private LocalDateTime actualStartTime;

    @ExcelProperty(value = "实际结束时间")
    private LocalDateTime actualEndTime;

    @ExcelProperty(value = "操作人")
    private String operator;

    @ExcelProperty(value = "备注")
    private String remark;

}
