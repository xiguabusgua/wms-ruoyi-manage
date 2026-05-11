package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.PurchaseOrder;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购订单视图对象 wms_purchase_order
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = PurchaseOrder.class)
public class PurchaseOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "订单编号")
    private String orderNo;

    @ExcelProperty(value = "关联申请ID")
    private Long requisitionId;

    @ExcelProperty(value = "供应商ID")
    private Long supplierId;

    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    @ExcelProperty(value = "订单日期")
    private LocalDate orderDate;

    @ExcelProperty(value = "期望到货日期")
    private LocalDate expectArrivalDate;

    @ExcelProperty(value = "总金额")
    private BigDecimal totalAmount;

    @ExcelProperty(value = "订单状态")
    private Integer orderStatus;

    @ExcelProperty(value = "订单状态名称")
    private String orderStatusName;

    @ExcelProperty(value = "收货人")
    private String receiver;

    @ExcelProperty(value = "收货仓库ID")
    private Long warehouseId;

    @ExcelProperty(value = "仓库名称")
    private String warehouseName;

    @ExcelProperty(value = "备注")
    private String remark;

    private List<PurchaseOrderDetailVo> details;

}
