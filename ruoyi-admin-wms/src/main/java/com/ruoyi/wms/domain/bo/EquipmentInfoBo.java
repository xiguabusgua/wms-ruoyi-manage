package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.EquipmentInfo;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 设备台账业务对象 wms_equipment_info
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = EquipmentInfo.class, reverseConvertGenerate = false)
public class EquipmentInfoBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "设备编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String equipmentCode;

    @NotBlank(message = "设备名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String equipmentName;

    private Integer equipmentType;

    private Long categoryId;

    private String model;

    private String specification;

    private String manufacturer;

    private LocalDate purchaseDate;

    private String location;

    private Integer equipmentStatus;

    private BigDecimal power;

    private BigDecimal oeeTarget;

    private String remark;

}
