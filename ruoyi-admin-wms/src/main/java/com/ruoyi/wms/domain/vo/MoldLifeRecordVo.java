package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.MoldLifeRecord;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = MoldLifeRecord.class)
public class MoldLifeRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "模具ID")
    private Long moldId;

    @ExcelProperty(value = "模具编号")
    private String moldCode;

    @ExcelProperty(value = "模具名称")
    private String moldName;

    @ExcelProperty(value = "记录日期")
    private LocalDate recordDate;

    @ExcelProperty(value = "本次冲次")
    private BigDecimal strokeCount;

    @ExcelProperty(value = "累计冲次")
    private BigDecimal accumulateStroke;

    @ExcelProperty(value = "数据来源")
    private Integer source;

    @ExcelProperty(value = "数据来源名")
    private String sourceName;

    @ExcelProperty(value = "关联工单ID")
    private Long workOrderId;

    @ExcelProperty(value = "备注")
    private String remark;

}
