package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.QuotationOrderDetail;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报价单明细视图对象 wms_quotation_order_detail
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = QuotationOrderDetail.class)
public class QuotationOrderDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "报价单id")
    private Long orderId;

    @ExcelProperty(value = "规格id")
    private Long skuId;

    @ExcelProperty(value = "数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "单位成本")
    private BigDecimal unitCost;

    @ExcelProperty(value = "成本小计")
    private BigDecimal costSubtotal;

    @ExcelProperty(value = "单位报价")
    private BigDecimal unitPrice;

    @ExcelProperty(value = "报价小计")
    private BigDecimal quoteSubtotal;

    @ExcelProperty(value = "利润率(%)")
    private BigDecimal profitRate;

    @ExcelProperty(value = "交货周期(天)")
    private Integer leadTimeDays;

    @ExcelProperty(value = "备注")
    private String remark;

    private ItemSkuVo itemSku;

    private ItemVo item;

}
