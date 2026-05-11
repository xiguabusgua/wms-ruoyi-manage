package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.ProductionDailyReport;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 生产日报业务对象 wms_production_daily_report
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ProductionDailyReport.class, reverseConvertGenerate = false)
public class ProductionDailyReportBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "报告日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDate reportDate;

    @NotNull(message = "工单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long workOrderId;

    @NotNull(message = "物料ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long productId;

    private BigDecimal planQuantity;

    private BigDecimal completedQuantity;

    private BigDecimal defectiveQuantity;

    private BigDecimal yieldRate;

    private String operator;

    private String remark;

}
