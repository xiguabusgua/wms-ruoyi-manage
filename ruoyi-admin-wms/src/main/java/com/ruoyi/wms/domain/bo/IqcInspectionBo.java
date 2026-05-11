package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.IqcInspection;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = IqcInspection.class, reverseConvertGenerate = false)
public class IqcInspectionBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "检验任务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long taskId;

    private Long receiptOrderId;

    private Long supplierId;

    private String materialName;

    private String specModel;

    private String batchNo;

    private BigDecimal quantity;

    private BigDecimal inspectQuantity;

    private BigDecimal defectQuantity;

    private Integer conclusion;

    private Integer handleMethod;

    private String handleRemark;

    private String inspector;

    private LocalDateTime inspectTime;

}
