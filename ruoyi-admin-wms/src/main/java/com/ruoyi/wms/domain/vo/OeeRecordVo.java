package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.OeeRecord;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * OEE记录视图对象 wms_oee_record
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = OeeRecord.class)
public class OeeRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "设备ID")
    private Long equipmentId;

    @ExcelProperty(value = "设备名称")
    private String equipmentName;

    @ExcelProperty(value = "记录日期")
    private LocalDate recordDate;

    @ExcelProperty(value = "计划时间(min)")
    private Integer plannedTime;

    @ExcelProperty(value = "实际运行(min)")
    private Integer runTime;

    @ExcelProperty(value = "停机时间(min)")
    private Integer downtime;

    @ExcelProperty(value = "合格品数")
    private Integer goodCount;

    @ExcelProperty(value = "总产量")
    private Integer totalCount;

    @ExcelProperty(value = "可用率(%)")
    private BigDecimal availabilityRate;

    @ExcelProperty(value = "性能率(%)")
    private BigDecimal performanceRate;

    @ExcelProperty(value = "品质率(%)")
    private BigDecimal qualityRate;

    @ExcelProperty(value = "综合OEE(%)")
    private BigDecimal oee;

    @ExcelProperty(value = "故障次数")
    private Integer faultCount;

    @ExcelProperty(value = "备注")
    private String remark;

}
