package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_customer_complaint")
public class CustomerComplaint extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

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
