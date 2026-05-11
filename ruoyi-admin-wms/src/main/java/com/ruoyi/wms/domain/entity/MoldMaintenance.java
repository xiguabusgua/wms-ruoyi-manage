package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_mold_maintenance")
public class MoldMaintenance extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Long moldId;

    private Integer maintenanceType;

    private String maintenanceContent;

    private LocalDate maintenanceDate;

    private String maintainer;

    private BigDecimal cost;

    private LocalDate nextDate;

    private String remark;

}
