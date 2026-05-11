package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.WorkOrder;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工单/派工单业务对象 wms_work_order
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = WorkOrder.class, reverseConvertGenerate = false)
public class WorkOrderBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "工单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String workOrderNo;

    private Long planId;

    @NotNull(message = "物料不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long productId;

    @NotNull(message = "计划数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal plannedQuantity;

    private BigDecimal completedQuantity;

    private BigDecimal defectiveQuantity;

    private Long equipmentId;

    private Long moldId;

    private Integer workStatus;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    private String operator;

    private String remark;

}
