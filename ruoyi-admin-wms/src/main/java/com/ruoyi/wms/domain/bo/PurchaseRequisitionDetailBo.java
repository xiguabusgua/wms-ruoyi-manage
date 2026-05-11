package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.PurchaseRequisitionDetail;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 采购申请明细业务对象 wms_purchase_requisition_detail
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PurchaseRequisitionDetail.class, reverseConvertGenerate = false)
public class PurchaseRequisitionDetailBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "物料ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long itemId;

    @NotNull(message = "规格ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long skuId;

    @NotNull(message = "需求数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal amount;

    private LocalDate demandDate;

    private Long supplierId;

    private String remark;

}
