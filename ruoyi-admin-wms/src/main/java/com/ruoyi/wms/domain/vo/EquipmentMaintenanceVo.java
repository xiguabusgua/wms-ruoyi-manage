package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.EquipmentMaintenance;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 设备维护记录视图对象 wms_equipment_maintenance
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = EquipmentMaintenance.class)
public class EquipmentMaintenanceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "设备ID")
    private Long equipmentId;

    @ExcelProperty(value = "设备名称")
    private String equipmentName;

    @ExcelProperty(value = "维护类型")
    private Integer maintenanceType;

    @ExcelProperty(value = "维护类型名称")
    private String maintenanceTypeName;

    @ExcelProperty(value = "维护内容")
    private String maintenanceContent;

    @ExcelProperty(value = "维护日期")
    private LocalDate maintenanceDate;

    @ExcelProperty(value = "下次维护日期")
    private LocalDate nextDate;

    @ExcelProperty(value = "费用")
    private BigDecimal cost;

    @ExcelProperty(value = "状态")
    private Integer status;

    @ExcelProperty(value = "状态名称")
    private String statusName;

    @ExcelProperty(value = "操作人")
    private String operator;

    @ExcelProperty(value = "备注")
    private String remark;

}
