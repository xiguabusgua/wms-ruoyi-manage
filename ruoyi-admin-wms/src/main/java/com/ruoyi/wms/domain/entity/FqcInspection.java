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
@TableName("wms_fqc_inspection")
public class FqcInspection extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Long taskId;

    private Long receiptOrderId;

    private Long workOrderId;

    private Long productId;

    private String batchNo;

    private BigDecimal quantity;

    private BigDecimal defectQuantity;

    private String customerInspect;

    private String customerName;

    private Integer conclusion;

    private String inspector;

    private LocalDateTime inspectTime;

    private String remark;

}
