package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 库存预警对象 wms_inventory_alert
 *
 * @author zcc
 * @date 2024-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_inventory_alert")
public class InventoryAlert extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 物料ID
     */
    private Long itemId;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 仓库ID
     */
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理备注
     */
    private String handleRemark;
}
