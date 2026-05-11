package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.ShipmentOrder;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出库单业务对象 wms_shipment_order
 *
 * @author zcc
 * @date 2024-08-01
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ShipmentOrder.class, reverseConvertGenerate = false)
public class ShipmentOrderBo extends BaseOrderBo<ShipmentOrderDetailBo> {
    /**
     * 入库类型
     */
    @NotNull(message = "出库类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long optType;

    /**
     * 订单号
     */
    private String bizOrderNo;
    /**
     * 对接商户
     */
    private Long merchantId;
    /**
     * 仓库id
     */
    @NotNull(message = "仓库不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long warehouseId;

    /**
     * 出库类型(1销售出库2生产领料3其他出库)
     */
    private Integer shipmentType;
    /**
     * 关联销售订单ID
     */
    private Long salesOrderId;
    /**
     * 销售订单号
     */
    private String salesOrderNo;
}
