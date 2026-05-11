package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.ProductionPlan;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 生产计划业务对象 wms_production_plan
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ProductionPlan.class, reverseConvertGenerate = false)
public class ProductionPlanBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "计划编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String planNo;

    private Long salesOrderId;

    @NotNull(message = "物料不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long productId;

    @NotNull(message = "计划数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal planQuantity;

    private BigDecimal producedQuantity;

    private LocalDate planStartDate;

    private LocalDate planEndDate;

    private Integer priority;

    private Integer planStatus;

    private String remark;

}
