package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.PurchaseRequisition;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购申请视图对象 wms_purchase_requisition
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = PurchaseRequisition.class)
public class PurchaseRequisitionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "申请编号")
    private String requisitionNo;

    @ExcelProperty(value = "申请类型")
    private Integer requisitionType;

    @ExcelProperty(value = "申请类型名称")
    private String requisitionTypeName;

    @ExcelProperty(value = "申请人")
    private String applicant;

    @ExcelProperty(value = "申请日期")
    private LocalDate applyDate;

    @ExcelProperty(value = "部门ID")
    private Long deptId;

    @ExcelProperty(value = "部门名称")
    private String deptName;

    @ExcelProperty(value = "紧急程度")
    private Integer urgentLevel;

    @ExcelProperty(value = "紧急程度名称")
    private String urgentLevelName;

    @ExcelProperty(value = "总金额")
    private BigDecimal totalAmount;

    @ExcelProperty(value = "审批状态")
    private Integer approvalStatus;

    @ExcelProperty(value = "审批状态名称")
    private String approvalStatusName;

    @ExcelProperty(value = "审批人ID")
    private Long approverId;

    @ExcelProperty(value = "审批人名称")
    private String approverName;

    @ExcelProperty(value = "审批时间")
    private LocalDateTime approveTime;

    @ExcelProperty(value = "审批意见")
    private String approveRemark;

    @ExcelProperty(value = "备注")
    private String remark;

    private List<PurchaseRequisitionDetailVo> details;

}
