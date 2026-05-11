package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.PickingTask;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 拣货任务业务对象 wms_picking_task
 *
 * @author zcc
 * @date 2024-08-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PickingTask.class, reverseConvertGenerate = false)
public class PickingTaskBo extends BaseBo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 拣货任务编号
     */
    @NotBlank(message = "拣货任务编号不能为空", groups = { AddGroup.class })
    private String taskNo;

    /**
     * 出库单ID
     */
    @NotNull(message = "出库单ID不能为空", groups = { AddGroup.class })
    private Long shipmentOrderId;

    /**
     * 仓库ID
     */
    @NotNull(message = "仓库不能为空", groups = { AddGroup.class, EditGroup.class })
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
