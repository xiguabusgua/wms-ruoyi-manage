package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.RouteStep;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 工序步骤业务对象 wms_route_step
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = RouteStep.class, reverseConvertGenerate = false)
public class RouteStepBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "工序序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer stepNo;

    @NotBlank(message = "工序名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String stepName;

    private Long equipmentId;

    private Integer standardTime;

    private Integer setupTime;

    private BigDecimal laborCost;

    private String description;

    private String remark;

}
