package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.SalesOrderDetail;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售订单明细视图对象 wms_sales_order_detail
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = SalesOrderDetail.class)
public class SalesOrderDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "订单ID")
    private Long orderId;

    @ExcelProperty(value = "规格ID")
    private Long skuId;

    private String itemName;

    private String skuName;

    @ExcelProperty(value = "数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ExcelProperty(value = "金额")
    private BigDecimal amount;

    @ExcelProperty(value = "行交货日期")
    private LocalDate deliveryDate;

    @ExcelProperty(value = "备注")
    private String remark;

}
