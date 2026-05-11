package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.EquipmentMaintenance;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 设备维护记录业务对象 wms_equipment_maintenance
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = EquipmentMaintenance.class, reverseConvertGenerate = false)
public class EquipmentMaintenanceBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "设备ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long equipmentId;

    @NotNull(message = "维护类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer maintenanceType;

    private String maintenanceContent;

    private LocalDate maintenanceDate;

    private LocalDate nextDate;

    private BigDecimal cost;

    private Integer status;

    private String operator;

    private String remark;

}
