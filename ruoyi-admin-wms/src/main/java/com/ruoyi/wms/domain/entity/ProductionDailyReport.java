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
 * 生产日报对象 wms_production_daily_report
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_production_daily_report")
public class ProductionDailyReport extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 报告日期
     */
    private LocalDate reportDate;

    /**
     * 工单ID
     */
    private Long workOrderId;

    /**
     * 物料ID
     */
    private Long productId;

    /**
     * 计划数量
     */
    private BigDecimal planQuantity;

    /**
     * 完工数量
     */
    private BigDecimal completedQuantity;

    /**
     * 不良数量
     */
    private BigDecimal defectiveQuantity;

    /**
     * 良率
     */
    private BigDecimal yieldRate;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

}
