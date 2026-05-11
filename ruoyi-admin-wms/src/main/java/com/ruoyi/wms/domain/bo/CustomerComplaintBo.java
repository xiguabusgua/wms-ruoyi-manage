package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.CustomerComplaint;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CustomerComplaint.class, reverseConvertGenerate = false)
public class CustomerComplaintBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "投诉编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String complaintNo;

    private Long customerId;

    private Long orderId;

    private Long productId;

    private String complaintType;

    private String complaintDesc;

    private BigDecimal quantity;

    private LocalDate complaintDate;

    private String handler;

    private Integer handleStatus;

    private String handleResult;

    private LocalDate closeDate;

    private String remark;

}
