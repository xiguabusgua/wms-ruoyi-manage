package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.IqcInspection;
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
@AutoMapper(target = IqcInspection.class)
public class IqcInspectionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "检验任务ID")
    private Long taskId;

    @ExcelProperty(value = "入库单ID")
    private Long receiptOrderId;

    @ExcelProperty(value = "供应商ID")
    private Long supplierId;

    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    @ExcelProperty(value = "物料名称")
    private String materialName;

    @ExcelProperty(value = "规格型号")
    private String specModel;

    @ExcelProperty(value = "批次号")
    private String batchNo;

    @ExcelProperty(value = "来料数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "检验数量")
    private BigDecimal inspectQuantity;

    @ExcelProperty(value = "不良数")
    private BigDecimal defectQuantity;

    @ExcelProperty(value = "结论(1合格2不合格3让步接收)")
    private Integer conclusion;

    @ExcelProperty(value = "处理方式(1退货2换货3特采4筛选)")
    private Integer handleMethod;

    @ExcelProperty(value = "处理说明")
    private String handleRemark;

    @ExcelProperty(value = "检验员")
    private String inspector;

    @ExcelProperty(value = "检验时间")
    private LocalDateTime inspectTime;

}
