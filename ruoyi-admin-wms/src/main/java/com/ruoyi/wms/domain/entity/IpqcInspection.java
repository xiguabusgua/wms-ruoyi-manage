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
@TableName("wms_ipqc_inspection")
public class IpqcInspection extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Long taskId;

    private Integer inspectionSubType;

    private Long workOrderId;

    private String stationName;

    private Long productId;

    private Integer sampleSize;

    private Integer defectQuantity;

    private String isStopLine;

    private String stopReason;

    private String inspector;

    private LocalDateTime inspectTime;

    private String conclusion;

    private String remark;

}
