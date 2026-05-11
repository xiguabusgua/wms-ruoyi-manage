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
@TableName("wms_iqc_inspection")
public class IqcInspection extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Long taskId;

    private Long receiptOrderId;

    private Long supplierId;

    private String materialName;

    private String specModel;

    private String batchNo;

    private BigDecimal quantity;

    private BigDecimal inspectQuantity;

    private BigDecimal defectQuantity;

    private Integer conclusion;

    private Integer handleMethod;

    private String handleRemark;

    private String inspector;

    private LocalDateTime inspectTime;

}
