package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.MoldMaintenance;
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
@AutoMapper(target = MoldMaintenance.class)
public class MoldMaintenanceVo implements Serializable {

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

    @ExcelProperty(value = "保养类型")
    private Integer maintenanceType;

    @ExcelProperty(value = "保养类型名")
    private String maintenanceTypeName;

    @ExcelProperty(value = "保养内容")
    private String maintenanceContent;

    @ExcelProperty(value = "保养日期")
    private LocalDate maintenanceDate;

    @ExcelProperty(value = "保养人")
    private String maintainer;

    @ExcelProperty(value = "费用")
    private BigDecimal cost;

    @ExcelProperty(value = "下次保养日期")
    private LocalDate nextDate;

    @ExcelProperty(value = "备注")
    private String remark;

}
