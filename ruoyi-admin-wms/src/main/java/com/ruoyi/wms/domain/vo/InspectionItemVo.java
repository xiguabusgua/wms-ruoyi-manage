package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.InspectionItem;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = InspectionItem.class)
public class InspectionItemVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "检验任务ID")
    private Long taskId;

    @ExcelProperty(value = "项目名称")
    private String itemName;

    @ExcelProperty(value = "标准值")
    private String standardValue;

    @ExcelProperty(value = "单位")
    private String unit;

    @ExcelProperty(value = "上限")
    private BigDecimal upperLimit;

    @ExcelProperty(value = "下限")
    private BigDecimal lowerLimit;

    @ExcelProperty(value = "实测值")
    private BigDecimal measuredValue;

    @ExcelProperty(value = "结果(1合格2不合格)")
    private Integer result;

    @ExcelProperty(value = "备注")
    private String remark;

}
