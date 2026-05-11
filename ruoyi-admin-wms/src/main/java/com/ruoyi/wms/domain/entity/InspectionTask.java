package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_inspection_task")
public class InspectionTask extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String taskNo;

    private Integer inspectionType;

    private Long sourceOrderId;

    private String sourceOrderNo;

    private Long productId;

    private String batchNo;

    private BigDecimal quantity;

    private Integer taskStatus;

    private String inspector;

    private LocalDateTime inspectTime;

    private String conclusion;

    private String remark;

}
