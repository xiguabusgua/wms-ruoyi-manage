package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.MoldBorrow;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MoldBorrow.class, reverseConvertGenerate = false)
public class MoldBorrowBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "模具ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long moldId;

    @NotBlank(message = "借用工单号不能为空", groups = { AddGroup.class })
    private String borrowNo;

    @NotBlank(message = "借用人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String borrower;

    private LocalDateTime borrowDate;

    private LocalDateTime returnDate;

    private Integer borrowStatus;

    private Long workOrderId;

    private String returnCheckResult;

    private String remark;

}
