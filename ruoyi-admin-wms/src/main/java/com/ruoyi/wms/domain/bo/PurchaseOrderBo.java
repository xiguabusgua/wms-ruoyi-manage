package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.PurchaseOrder;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购订单业务对象 wms_purchase_order
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PurchaseOrder.class, reverseConvertGenerate = false)
public class PurchaseOrderBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderNo;

    private Long requisitionId;

    @NotNull(message = "供应商不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long supplierId;

    private LocalDate orderDate;

    private LocalDate expectArrivalDate;

    private BigDecimal totalAmount;

    private Integer orderStatus;

    private String receiver;

    private Long warehouseId;

    private String remark;

    /**
     * 采购订单明细列表
     */
    private List<PurchaseOrderDetailBo> details;

}
