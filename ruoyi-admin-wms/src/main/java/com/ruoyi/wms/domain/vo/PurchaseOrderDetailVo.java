package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.PurchaseOrderDetail;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购订单明细视图对象 wms_purchase_order_detail
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = PurchaseOrderDetail.class)
public class PurchaseOrderDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "采购订单ID")
    private Long orderId;

    @ExcelProperty(value = "物料ID")
    private Long itemId;

    @ExcelProperty(value = "物料名称")
    private String itemName;

    @ExcelProperty(value = "规格ID")
    private Long skuId;

    @ExcelProperty(value = "规格名称")
    private String skuName;

    @ExcelProperty(value = "采购数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ExcelProperty(value = "已收数量")
    private BigDecimal receivedQuantity;

    @ExcelProperty(value = "金额")
    private BigDecimal amount;

    @ExcelProperty(value = "备注")
    private String remark;

}
