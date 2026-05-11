package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.EquipmentCategory;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备分类业务对象 wms_equipment_category
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = EquipmentCategory.class, reverseConvertGenerate = false)
public class EquipmentCategoryBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    private Long parentId;

    @NotBlank(message = "分类名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String categoryName;

    private Integer orderNum;

    private String status;

}
