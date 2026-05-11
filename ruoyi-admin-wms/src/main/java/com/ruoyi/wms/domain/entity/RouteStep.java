package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 工序步骤对象 wms_route_step
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_route_step")
public class RouteStep extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 工艺路线ID
     */
    private Long routeId;

    /**
     * 工序序号
     */
    private Integer stepNo;

    /**
     * 工序名称
     */
    private String stepName;

    /**
     * 所需设备ID
     */
    private Long equipmentId;

    /**
     * 标准工时(分钟)
     */
    private Integer standardTime;

    /**
     * 准备时间(分钟)
     */
    private Integer setupTime;

    /**
     * 人工成本
     */
    private BigDecimal laborCost;

    /**
     * 工序说明
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

}
