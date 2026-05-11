package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.PurchaseRequisitionDetail;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 采购申请明细视图对象 wms_purchase_requisition_detail
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = PurchaseRequisitionDetail.class)
public class PurchaseRequisitionDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "采购申请ID")
    private Long requisitionId;

    @ExcelProperty(value = "物料ID")
    private Long itemId;

    @ExcelProperty(value = "物料名称")
    private String itemName;

    @ExcelProperty(value = "规格ID")
    private Long skuId;

    @ExcelProperty(value = "规格名称")
    private String skuName;

    @ExcelProperty(value = "需求数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "预估单价")
    private BigDecimal unitPrice;

    @ExcelProperty(value = "预估金额")
    private BigDecimal amount;

    @ExcelProperty(value = "需求日期")
    private LocalDate demandDate;

    @ExcelProperty(value = "建议供应商ID")
    private Long supplierId;

    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    @ExcelProperty(value = "备注")
    private String remark;

}
