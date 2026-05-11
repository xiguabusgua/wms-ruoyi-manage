package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.QuotationCost;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 成本核算明细业务对象 wms_quotation_cost
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = QuotationCost.class, reverseConvertGenerate = false)
public class QuotationCostBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "报价单id不能为空")
    private Long quotationOrderId;

    @NotNull(message = "规格id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long skuId;

    private BigDecimal materialCost;

    private BigDecimal laborCost;

    private BigDecimal manufacturingCost;

    private BigDecimal otherCost;

    private BigDecimal totalCost;

    private String bomVersion;

    private String processRoute;

    private String remark;

}
