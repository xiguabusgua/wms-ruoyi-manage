package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.InspectionTask;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InspectionTask.class, reverseConvertGenerate = false)
public class InspectionTaskBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "任务编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String taskNo;

    @NotNull(message = "检验类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer inspectionType;

    private Long sourceOrderId;

    private String sourceOrderNo;

    private Long productId;

    private String batchNo;

    private BigDecimal quantity;

    private Integer taskStatus;

    private String inspector;

    private LocalDateTime inspectTime;

    private String conclusion;

    private String remark;

    private List<InspectionItemBo> items;

}
