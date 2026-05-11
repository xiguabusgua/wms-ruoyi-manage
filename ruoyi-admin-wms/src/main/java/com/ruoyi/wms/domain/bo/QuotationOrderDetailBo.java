package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.QuotationOrderDetail;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 报价单明细业务对象 wms_quotation_order_detail
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = QuotationOrderDetail.class, reverseConvertGenerate = false)
public class QuotationOrderDetailBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "规格id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long skuId;

    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal quantity;

    private BigDecimal unitCost;

    private BigDecimal costSubtotal;

    @NotNull(message = "单位报价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal unitPrice;

    private BigDecimal quoteSubtotal;

    private BigDecimal profitRate;

    private Integer leadTimeDays;

    private String remark;

}
