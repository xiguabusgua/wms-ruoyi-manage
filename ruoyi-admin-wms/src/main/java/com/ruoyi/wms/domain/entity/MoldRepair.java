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
@TableName("wms_mold_repair")
public class MoldRepair extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Long moldId;

    private String repairApplyNo;

    private String faultDescription;

    private String repairContent;

    private String replacedParts;

    private LocalDate repairStartDate;

    private LocalDate repairEndDate;

    private Integer repairStatus;

    private BigDecimal cost;

    private String repairer;

    private String remark;

}
