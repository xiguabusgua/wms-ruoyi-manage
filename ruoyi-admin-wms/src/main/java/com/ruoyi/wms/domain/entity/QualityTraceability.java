package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_quality_traceability")
public class QualityTraceability extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String traceNo;

    private Long productId;

    private String batchNo;

    private String serialNo;

    private String sourceMaterialBatch;

    private Long workOrderId;

    private Long iqcTaskId;

    private Long ipqcTaskId;

    private Long fqcTaskId;

}
