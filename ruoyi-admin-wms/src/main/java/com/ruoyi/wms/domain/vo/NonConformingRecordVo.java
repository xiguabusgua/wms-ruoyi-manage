package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.NonConformingRecord;
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
@AutoMapper(target = NonConformingRecord.class)
public class NonConformingRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "记录编号")
    private String recordNo;

    @ExcelProperty(value = "检验类型(1IQC2IPQC3FQC)")
    private Integer inspectionType;

    @ExcelProperty(value = "检验任务ID")
    private Long inspectionTaskId;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "物料名称")
    private String productName;

    @ExcelProperty(value = "缺陷类型")
    private String defectType;

    @ExcelProperty(value = "缺陷描述")
    private String defectDesc;

    @ExcelProperty(value = "缺陷数量")
    private BigDecimal defectQuantity;

    @ExcelProperty(value = "严重程度(1轻微2一般3严重)")
    private Integer severity;

    @ExcelProperty(value = "处置方式(1返工2报废3特采4让步)")
    private Integer disposition;

    @ExcelProperty(value = "操作人")
    private String operator;

    @ExcelProperty(value = "发生时间")
    private LocalDateTime occurTime;

    @ExcelProperty(value = "备注")
    private String remark;

}
