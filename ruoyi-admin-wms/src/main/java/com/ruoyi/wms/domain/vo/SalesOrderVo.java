package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.SalesOrder;
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
 * 销售订单视图对象 wms_sales_order
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = SalesOrder.class)
public class SalesOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "订单编号")
    private String orderNo;

    @ExcelProperty(value = "客户ID")
    private Long customerId;

    private String customerName;

    @ExcelProperty(value = "订单日期")
    private LocalDateTime orderDate;

    @ExcelProperty(value = "交货日期")
    private LocalDate deliveryDate;

    @ExcelProperty(value = "总数量")
    private BigDecimal totalQuantity;

    @ExcelProperty(value = "总金额")
    private BigDecimal totalAmount;

    @ExcelProperty(value = "订单状态")
    private Integer orderStatus;

    private String orderStatusName;

    @ExcelProperty(value = "关联报价单ID")
    private Long quotationOrderId;

    @ExcelProperty(value = "备注")
    private String remark;

    private List<SalesOrderDetailVo> details;

}
