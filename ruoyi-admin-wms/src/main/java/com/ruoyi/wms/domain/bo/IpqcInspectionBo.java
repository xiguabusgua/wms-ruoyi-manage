package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.IpqcInspection;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = IpqcInspection.class, reverseConvertGenerate = false)
public class IpqcInspectionBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "检验任务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long taskId;

    private Integer inspectionSubType;

    private Long workOrderId;

    private String stationName;

    private Long productId;

    private Integer sampleSize;

    private Integer defectQuantity;

    private String isStopLine;

    private String stopReason;

    private String inspector;

    private LocalDateTime inspectTime;

    private String conclusion;

    private String remark;

}
