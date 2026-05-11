package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 报价单对象 wms_quotation_order
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_quotation_order")
public class QuotationOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 报价单号
     */
    private String orderNo;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 报价有效期至
     */
    private LocalDateTime validUntil;

    /**
     * 产品总数
     */
    private BigDecimal totalQuantity;

    /**
     * 总成本金额
     */
    private BigDecimal totalCostAmount;

    /**
     * 总报价金额
     */
    private BigDecimal totalQuoteAmount;

    /**
     * 利润率(%)
     */
    private BigDecimal profitRate;

    /**
     * 报价状态 0-草稿 1-待审批 2-已审批通过 3-已驳回 4-已转订单 5-已失效
     */
    private Integer orderStatus;

    /**
     * 审批人
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
     * 关联的出库订单号（转订单后）
     */
    private Long relatedShipmentOrderId;

    /**
     * 备注
     */
    private String remark;

}
