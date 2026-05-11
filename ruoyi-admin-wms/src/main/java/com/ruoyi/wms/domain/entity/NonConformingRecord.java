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
@TableName("wms_non_conforming_record")
public class NonConformingRecord extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String recordNo;

    private Integer inspectionType;

    private Long inspectionTaskId;

    private Long productId;

    private String defectType;

    private String defectDesc;

    private BigDecimal defectQuantity;

    private Integer severity;

    private Integer disposition;

    private String operator;

    private LocalDateTime occurTime;

    private String remark;

}
