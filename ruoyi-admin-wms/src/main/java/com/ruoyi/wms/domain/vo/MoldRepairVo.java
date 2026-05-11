package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.MoldRepair;
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
@AutoMapper(target = MoldRepair.class)
public class MoldRepairVo implements Serializable {

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

    @ExcelProperty(value = "维修申请单号")
    private String repairApplyNo;

    @ExcelProperty(value = "故障描述")
    private String faultDescription;

    @ExcelProperty(value = "维修内容")
    private String repairContent;

    @ExcelProperty(value = "更换零件")
    private String replacedParts;

    @ExcelProperty(value = "维修开始日期")
    private LocalDate repairStartDate;

    @ExcelProperty(value = "维修结束日期")
    private LocalDate repairEndDate;

    @ExcelProperty(value = "维修状态")
    private Integer repairStatus;

    @ExcelProperty(value = "维修状态名")
    private String repairStatusName;

    @ExcelProperty(value = "维修费")
    private BigDecimal cost;

    @ExcelProperty(value = "维修人")
    private String repairer;

    @ExcelProperty(value = "备注")
    private String remark;

}
