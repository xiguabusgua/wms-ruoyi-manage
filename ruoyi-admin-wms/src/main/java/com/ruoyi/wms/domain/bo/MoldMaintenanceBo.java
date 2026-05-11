package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.MoldMaintenance;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MoldMaintenance.class, reverseConvertGenerate = false)
public class MoldMaintenanceBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "模具ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long moldId;

    private Integer maintenanceType;

    @NotBlank(message = "保养内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maintenanceContent;

    private LocalDate maintenanceDate;

    private String maintainer;

    private BigDecimal cost;

    private LocalDate nextDate;

    private String remark;

}
