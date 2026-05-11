package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.EquipmentCategory;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设备分类视图对象 wms_equipment_category
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = EquipmentCategory.class)
public class EquipmentCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "父分类ID")
    private Long parentId;

    @ExcelProperty(value = "分类名称")
    private String categoryName;

    @ExcelProperty(value = "排序")
    private Integer orderNum;

    @ExcelProperty(value = "状态")
    private String status;

}
