package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.WorkReport;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 报工记录业务对象 wms_work_report
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = WorkReport.class, reverseConvertGenerate = false)
public class WorkReportBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "工单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long workOrderId;

    @NotNull(message = "报工类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer reportType;

    private LocalDateTime reportTime;

    private BigDecimal quantity;

    private BigDecimal defectiveQuantity;

    @NotBlank(message = "操作人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String operator;

    private String remark;

}
