package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.InventoryAlert;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 库存预警业务对象 wms_inventory_alert
 *
 * @author zcc
 * @date 2024-08-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InventoryAlert.class, reverseConvertGenerate = false)
public class InventoryAlertBo extends BaseBo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 物料ID
     */
    @NotNull(message = "物料ID不能为空", groups = { AddGroup.class })
    private Long itemId;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 仓库ID
     */
    @NotNull(message = "仓库不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long warehouseId;

    /**
     * 当前数量
     */
    private BigDecimal currentQty;

    /**
     * 安全库存
     */
    private BigDecimal minSafetyStock;

    /**
     * 预警类型(1不足2超储)
     */
    private Integer alertType;

    /**
     * 预警级别(1一般2紧急)
     */
    private Integer alertLevel;

    /**
     * 预警状态(0未处理1已处理)
     */
    private Integer alertStatus;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理备注
     */
    private String handleRemark;
}
