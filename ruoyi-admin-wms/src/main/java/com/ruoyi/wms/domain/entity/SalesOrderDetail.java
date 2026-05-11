package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售订单明细对象 wms_sales_order_detail
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_sales_order_detail")
public class SalesOrderDetail extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 行交货日期
     */
    private LocalDate deliveryDate;

    /**
     * 备注
     */
    private String remark;

}
