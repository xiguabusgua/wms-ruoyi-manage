package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.MoldRepair;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MoldRepair.class, reverseConvertGenerate = false)
public class MoldRepairBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "模具ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long moldId;

    @NotBlank(message = "维修申请单号不能为空", groups = { AddGroup.class })
    private String repairApplyNo;

    @NotBlank(message = "故障描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String faultDescription;

    private String repairContent;

    private String replacedParts;

    private LocalDate repairStartDate;

    private LocalDate repairEndDate;

    private Integer repairStatus;

    private BigDecimal cost;

    private String repairer;

    private String remark;

}
