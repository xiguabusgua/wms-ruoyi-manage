package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.FqcInspection;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = FqcInspection.class)
public class FqcInspectionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "检验任务ID")
    private Long taskId;

    @ExcelProperty(value = "入库单ID(生产入库)")
    private Long receiptOrderId;

    @ExcelProperty(value = "工单ID")
    private Long workOrderId;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "批次号")
    private String batchNo;

    @ExcelProperty(value = "检验数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "不良数")
    private BigDecimal defectQuantity;

    @ExcelProperty(value = "是否客户验货(Y/N)")
    private String customerInspect;

    @ExcelProperty(value = "客户名称")
    private String customerName;

    @ExcelProperty(value = "结论(1合格2不合格)")
    private Integer conclusion;

    @ExcelProperty(value = "检验员")
    private String inspector;

    @ExcelProperty(value = "检验时间")
    private LocalDateTime inspectTime;

    @ExcelProperty(value = "备注")
    private String remark;

}
