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
 * 销售订单对象 wms_sales_order
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_sales_order")
public class SalesOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 订单日期
     */
    private LocalDateTime orderDate;

    /**
     * 交货日期
     */
    private LocalDate deliveryDate;

    /**
     * 总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态 0待审批 1已审批 2生产中 3已发货 4已完成 5已取消
     */
    private Integer orderStatus;

    /**
     * 关联报价单ID
     */
    private Long quotationOrderId;

    /**
     * 备注
     */
    private String remark;

}
