package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.PurchaseOrderDetail;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 采购订单明细业务对象 wms_purchase_order_detail
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PurchaseOrderDetail.class, reverseConvertGenerate = false)
public class PurchaseOrderDetailBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "物料ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long itemId;

    @NotNull(message = "规格ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long skuId;

    @NotNull(message = "采购数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal receivedQuantity;

    private BigDecimal amount;

    private String remark;

}
