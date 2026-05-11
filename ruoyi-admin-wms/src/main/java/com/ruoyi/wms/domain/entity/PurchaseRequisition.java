package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购申请对象 wms_purchase_requisition
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_purchase_requisition")
public class PurchaseRequisition extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 申请编号
     */
    private String requisitionNo;

    /**
     * 申请类型 1生产用料 2办公用品 3设备备件 4其他
     */
    private Integer requisitionType;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 申请日期
     */
    private LocalDate applyDate;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 紧急程度 1正常 2紧急 3特急
     */
    private Integer urgentLevel;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 审批状态 0待审批 1已审批 2已驳回 3已关闭
     */
    private Integer approvalStatus;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批时间
     */
    private LocalDateTime approveTime;

    /**
     * 审批意见
     */
    private String approveRemark;

    /**
     * 备注
     */
    private String remark;

}
