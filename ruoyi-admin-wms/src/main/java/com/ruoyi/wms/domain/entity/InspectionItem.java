package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_inspection_item")
public class InspectionItem extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Long taskId;

    private String itemName;

    private String standardValue;

    private String unit;

    private BigDecimal upperLimit;

    private BigDecimal lowerLimit;

    private BigDecimal measuredValue;

    private Integer result;

    private String remark;

}
