package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.OeeRecord;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * OEE记录业务对象 wms_oee_record
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OeeRecord.class, reverseConvertGenerate = false)
public class OeeRecordBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "设备ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long equipmentId;

    @NotNull(message = "记录日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDate recordDate;

    private Integer plannedTime;

    private Integer runTime;

    private Integer downtime;

    private Integer goodCount;

    private Integer totalCount;

    private BigDecimal availabilityRate;

    private BigDecimal performanceRate;

    private BigDecimal qualityRate;

    private BigDecimal oee;

    private Integer faultCount;

    private String remark;

}
