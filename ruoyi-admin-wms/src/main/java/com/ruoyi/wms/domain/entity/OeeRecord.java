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
 * OEE记录对象 wms_oee_record
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_oee_record")
public class OeeRecord extends BaseEntity {

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
     * 记录日期
     */
    private LocalDate recordDate;

    /**
     * 计划时间(分钟)
     */
    private Integer plannedTime;

    /**
     * 实际运行时间(分钟)
     */
    private Integer runTime;

    /**
     * 停机时间(分钟)
     */
    private Integer downtime;

    /**
     * 合格品数
     */
    private Integer goodCount;

    /**
     * 总产量
     */
    private Integer totalCount;

    /**
     * 可用率
     */
    private BigDecimal availabilityRate;

    /**
     * 性能率
     */
    private BigDecimal performanceRate;

    /**
     * 品质率
     */
    private BigDecimal qualityRate;

    /**
     * 综合效率(OEE)
     */
    private BigDecimal oee;

    /**
     * 故障次数
     */
    private Integer faultCount;

    /**
     * 备注
     */
    private String remark;

}
