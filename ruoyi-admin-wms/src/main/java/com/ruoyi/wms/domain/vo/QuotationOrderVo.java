package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.QuotationOrder;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 报价单视图对象 wms_quotation_order
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = QuotationOrder.class)
public class QuotationOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "报价单号")
    private String orderNo;

    @ExcelProperty(value = "客户id")
    private Long customerId;

    @ExcelProperty(value = "客户名称")
    private String customerName;

    @ExcelProperty(value = "联系人")
    private String contactPerson;

    @ExcelProperty(value = "联系电话")
    private String contactPhone;

    @ExcelProperty(value = "报价有效期至")
    private LocalDateTime validUntil;

    @ExcelProperty(value = "产品总数")
    private BigDecimal totalQuantity;

    @ExcelProperty(value = "总成本金额")
    private BigDecimal totalCostAmount;

    @ExcelProperty(value = "总报价金额")
    private BigDecimal totalQuoteAmount;

    @ExcelProperty(value = "利润率(%)")
    private BigDecimal profitRate;

    @ExcelProperty(value = "报价状态")
    private Integer orderStatus;

    @ExcelProperty(value = "审批人id")
    private Long approverId;

    @ExcelProperty(value = "审批人名称")
    private String approverName;

    @ExcelProperty(value = "审批时间")
    private LocalDateTime approveTime;

    @ExcelProperty(value = "审批意见")
    private String approveRemark;

    @ExcelProperty(value = "关联出库订单号")
    private Long relatedShipmentOrderId;

    @ExcelProperty(value = "备注")
    private String remark;

    private List<QuotationOrderDetailVo> details;

}
