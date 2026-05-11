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
 * 报工记录对象 wms_work_report
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_work_report")
public class WorkReport extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 工单ID
     */
    private Long workOrderId;

    /**
     * 报工类型 1-开工 2-完工
     */
    private Integer reportType;

    /**
     * 报工时间
     */
    private LocalDateTime reportTime;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 不良数量
     */
    private BigDecimal defectiveQuantity;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

}
