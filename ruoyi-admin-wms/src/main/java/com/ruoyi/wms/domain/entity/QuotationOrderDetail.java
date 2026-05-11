package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 报价单明细对象 wms_quotation_order_detail
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_quotation_order_detail")
public class QuotationOrderDetail extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 报价单id
     */
    private Long orderId;

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 成本小计
     */
    private BigDecimal costSubtotal;

    /**
     * 单位报价
     */
    private BigDecimal unitPrice;

    /**
     * 报价小计
     */
    private BigDecimal quoteSubtotal;

    /**
     * 利润率(%)
     */
    private BigDecimal profitRate;

    /**
     * 交货周期(天)
     */
    private Integer leadTimeDays;

    /**
     * 备注
     */
    private String remark;

}
