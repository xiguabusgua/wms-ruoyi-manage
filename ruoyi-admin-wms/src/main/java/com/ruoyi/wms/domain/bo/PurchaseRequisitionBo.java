package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.PurchaseRequisition;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购申请业务对象 wms_purchase_requisition
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PurchaseRequisition.class, reverseConvertGenerate = false)
public class PurchaseRequisitionBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "申请编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String requisitionNo;

    private Integer requisitionType;

    private String applicant;

    private LocalDate applyDate;

    private Long deptId;

    private Integer urgentLevel;

    private BigDecimal totalAmount;

    private Integer approvalStatus;

    private Long approverId;

    private String approveRemark;

    private String remark;

    /**
     * 采购申请明细列表
     */
    private List<PurchaseRequisitionDetailBo> details;

}
