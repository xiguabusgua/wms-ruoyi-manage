package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.QuotationOrder;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 报价单业务对象 wms_quotation_order
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = QuotationOrder.class, reverseConvertGenerate = false)
public class QuotationOrderBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "报价单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderNo;

    @NotNull(message = "客户不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long customerId;

    private String contactPerson;

    private String contactPhone;

    private LocalDateTime validUntil;

    private BigDecimal totalQuantity;

    private BigDecimal totalCostAmount;

    private BigDecimal totalQuoteAmount;

    private BigDecimal profitRate;

    private Integer orderStatus;

    private Long approverId;

    private LocalDateTime approveTime;

    private String approveRemark;

    private Long relatedShipmentOrderId;

    private String remark;

    /**
     * 报价明细列表
     */
    private List<QuotationOrderDetailBo> details;

}
