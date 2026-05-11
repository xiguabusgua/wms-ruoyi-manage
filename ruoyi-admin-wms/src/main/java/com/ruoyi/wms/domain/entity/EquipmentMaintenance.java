package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 设备维护记录对象 wms_equipment_maintenance
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_equipment_maintenance")
public class EquipmentMaintenance extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 设备ID
     */
    private Long equipmentId;

    /**
     * 维护类型 1保养 2维修 3故障
     */
    private Integer maintenanceType;

    /**
     * 维护内容
     */
    private String maintenanceContent;

    /**
     * 维护日期
     */
    private LocalDate maintenanceDate;

    /**
     * 下次维护日期
     */
    private LocalDate nextDate;

    /**
     * 费用
     */
    private BigDecimal cost;

    /**
     * 状态 0计划中 1进行中 2完成
     */
    private Integer status;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

}
