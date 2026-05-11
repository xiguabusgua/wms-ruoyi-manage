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
 * 生产计划对象 wms_production_plan
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_production_plan")
public class ProductionPlan extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 计划编号
     */
    private String planNo;

    /**
     * 关联销售订单ID
     */
    private Long salesOrderId;

    /**
     * 物料ID
     */
    private Long productId;

    /**
     * 计划数量
     */
    private BigDecimal planQuantity;

    /**
     * 已生产数量
     */
    private BigDecimal producedQuantity;

    /**
     * 计划开始日期
     */
    private LocalDate planStartDate;

    /**
     * 计划结束日期
     */
    private LocalDate planEndDate;

    /**
     * 优先级 1-低 2-中 3-高
     */
    private Integer priority;

    /**
     * 计划状态 0-待排产 1-已排产 2-生产中 3-已完成 4-已关闭
     */
    private Integer planStatus;

    /**
     * 备注
     */
    private String remark;

}
