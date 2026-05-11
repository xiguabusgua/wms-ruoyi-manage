package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.EquipmentInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 设备台账视图对象 wms_equipment_info
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = EquipmentInfo.class)
public class EquipmentInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "设备编码")
    private String equipmentCode;

    @ExcelProperty(value = "设备名称")
    private String equipmentName;

    @ExcelProperty(value = "设备类型")
    private Integer equipmentType;

    @ExcelProperty(value = "设备类型名称")
    private String equipmentTypeName;

    @ExcelProperty(value = "分类ID")
    private Long categoryId;

    @ExcelProperty(value = "分类名称")
    private String categoryName;

    @ExcelProperty(value = "型号")
    private String model;

    @ExcelProperty(value = "规格参数")
    private String specification;

    @ExcelProperty(value = "制造商")
    private String manufacturer;

    @ExcelProperty(value = "购买日期")
    private LocalDate purchaseDate;

    @ExcelProperty(value = "安装位置")
    private String location;

    @ExcelProperty(value = "设备状态")
    private Integer equipmentStatus;

    @ExcelProperty(value = "设备状态名称")
    private String equipmentStatusName;

    @ExcelProperty(value = "功率(kW)")
    private BigDecimal power;

    @ExcelProperty(value = "目标OEE")
    private BigDecimal oeeTarget;

    @ExcelProperty(value = "备注")
    private String remark;

}
