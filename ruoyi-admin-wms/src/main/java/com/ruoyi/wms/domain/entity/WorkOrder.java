package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工单/派工单对象 wms_work_order
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_work_order")
public class WorkOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 工单号
     */
    private String workOrderNo;

    /**
     * 生产计划ID
     */
    private Long planId;

    /**
     * 物料ID
     */
    private Long productId;

    /**
     * 计划数量
     */
    private BigDecimal plannedQuantity;

    /**
     * 完工数量
     */
    private BigDecimal completedQuantity;

    /**
     * 不良数量
     */
    private BigDecimal defectiveQuantity;

    /**
     * 设备ID
     */
    private Long equipmentId;

    /**
     * 模具ID
     */
    private Long moldId;

    /**
     * 工单状态 0-待生产 1-生产中 2-暂停 3-已完成 4-已取消
     */
    private Integer workStatus;

    /**
     * 实际开始时间
     */
    private LocalDateTime actualStartTime;

    /**
     * 实际结束时间
     */
    private LocalDateTime actualEndTime;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

}
