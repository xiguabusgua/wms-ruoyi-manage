package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.NonConformingRecord;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = NonConformingRecord.class, reverseConvertGenerate = false)
public class NonConformingRecordBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "记录编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String recordNo;

    private Integer inspectionType;

    private Long inspectionTaskId;

    private Long productId;

    private String defectType;

    private String defectDesc;

    private BigDecimal defectQuantity;

    private Integer severity;

    private Integer disposition;

    private String operator;

    private LocalDateTime occurTime;

    private String remark;

}
