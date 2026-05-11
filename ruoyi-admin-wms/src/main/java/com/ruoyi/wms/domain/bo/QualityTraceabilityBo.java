package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.QualityTraceability;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = QualityTraceability.class, reverseConvertGenerate = false)
public class QualityTraceabilityBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "追溯编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String traceNo;

    @NotNull(message = "物料ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long productId;

    private String batchNo;

    private String serialNo;

    private String sourceMaterialBatch;

    private Long workOrderId;

    private Long iqcTaskId;

    private Long ipqcTaskId;

    private Long fqcTaskId;

}
