package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.InventoryAlert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 库存预警视图对象 wms_inventory_alert
 *
 * @author zcc
 * @date 2024-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = InventoryAlert.class)
public class InventoryAlertVo extends BaseVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 物料ID
     */
    @ExcelProperty(value = "物料ID")
    private Long itemId;

    /**
     * 规格ID
     */
    @ExcelProperty(value = "规格ID")
    private Long skuId;

    /**
     * 仓库ID
     */
    @ExcelProperty(value = "仓库ID")
    private Long warehouseId;

    /**
     * 当前数量
     */
    @ExcelProperty(value = "当前数量")
    private BigDecimal currentQty;

    /**
     * 安全库存
     */
    @ExcelProperty(value = "安全库存")
    private BigDecimal minSafetyStock;

    /**
     * 预警类型(1不足2超储)
     */
    @ExcelProperty(value = "预警类型")
    private Integer alertType;

    /**
     * 预警类型名称
     */
    private String alertTypeName;

    /**
     * 预警级别(1一般2紧急)
     */
    @ExcelProperty(value = "预警级别")
    private Integer alertLevel;

    /**
     * 预警级别名称
     */
    private String alertLevelName;

    /**
     * 预警状态(0未处理1已处理)
     */
    @ExcelProperty(value = "预警状态")
    private Integer alertStatus;

    /**
     * 预警状态名称
     */
    private String alertStatusName;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 处理时间
     */
    @ExcelProperty(value = "处理时间")
    private LocalDateTime handleTime;

    /**
     * 处理备注
     */
    @ExcelProperty(value = "处理备注")
    private String handleRemark;
}
