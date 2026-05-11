package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.MoldLifeRecord;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MoldLifeRecord.class, reverseConvertGenerate = false)
public class MoldLifeRecordBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "模具ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long moldId;

    private LocalDate recordDate;

    @NotNull(message = "本次冲次不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal strokeCount;

    private BigDecimal accumulateStroke;

    private Integer source;

    private Long workOrderId;

    private String remark;

}
