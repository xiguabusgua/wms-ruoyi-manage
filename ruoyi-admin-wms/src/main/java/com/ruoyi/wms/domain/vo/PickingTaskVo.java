package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.PickingTask;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 拣货任务视图对象 wms_picking_task
 *
 * @author zcc
 * @date 2024-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = PickingTask.class)
public class PickingTaskVo extends BaseVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 拣货任务编号
     */
    @ExcelProperty(value = "拣货任务编号")
    private String taskNo;

    /**
     * 出库单ID
     */
    @ExcelProperty(value = "出库单ID")
    private Long shipmentOrderId;

    /**
     * 仓库ID
     */
    @ExcelProperty(value = "仓库ID")
    private Long warehouseId;

    /**
     * 拣货人
     */
    @ExcelProperty(value = "拣货人")
    private String picker;

    /**
     * 拣货状态(0待拣货1拣货中2已完成)
     */
    @ExcelProperty(value = "拣货状态")
    private Integer pickStatus;

    /**
     * 拣货状态名称
     */
    private String pickStatusName;

    /**
     * 拣货时间
     */
    @ExcelProperty(value = "拣货时间")
    private LocalDateTime pickTime;

    /**
     * 完成时间
     */
    @ExcelProperty(value = "完成时间")
    private LocalDateTime completeTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
}
