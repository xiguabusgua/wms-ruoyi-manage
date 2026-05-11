package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.QualityTraceability;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = QualityTraceability.class)
public class QualityTraceabilityVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "追溯编号")
    private String traceNo;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "物料名称")
    private String productName;

    @ExcelProperty(value = "批次号")
    private String batchNo;

    @ExcelProperty(value = "序列号")
    private String serialNo;

    @ExcelProperty(value = "原材料批次")
    private String sourceMaterialBatch;

    @ExcelProperty(value = "生产工单")
    private Long workOrderId;

    @ExcelProperty(value = "IQC任务ID")
    private Long iqcTaskId;

    @ExcelProperty(value = "IPQC任务ID")
    private Long ipqcTaskId;

    @ExcelProperty(value = "FQC任务ID")
    private Long fqcTaskId;

    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
