package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 拣货任务对象 wms_picking_task
 *
 * @author zcc
 * @date 2024-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_picking_task")
public class PickingTask extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 拣货任务编号
     */
    private String taskNo;
    /**
     * 出库单ID
     */
    private Long shipmentOrderId;
    /**
     * 仓库ID
     */
    private Long warehouseId;
    /**
     * 拣货人
     */
    private String picker;
    /**
     * 拣货状态(0待拣货1拣货中2已完成)
     */
    private Integer pickStatus;
    /**
     * 拣货时间
     */
    private LocalDateTime pickTime;
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    /**
     * 备注
     */
    private String remark;
}
