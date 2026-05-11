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
 * 设备台账对象 wms_equipment_info
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_equipment_info")
public class EquipmentInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 设备编码
     */
    private String equipmentCode;

    /**
     * 设备名称
     */
    private String equipmentName;

    /**
     * 设备类型 1冲床 2剪板机 3折弯机 4其他
     */
    private Integer equipmentType;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格参数
     */
    private String specification;

    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 购买日期
     */
    private LocalDate purchaseDate;

    /**
     * 安装位置
     */
    private String location;

    /**
     * 设备状态 0运行 1停机 2维修中 3故障 4封存
     */
    private Integer equipmentStatus;

    /**
     * 功率(kW)
     */
    private BigDecimal power;

    /**
     * 目标OEE
     */
    private BigDecimal oeeTarget;

    /**
     * 备注
     */
    private String remark;

}
