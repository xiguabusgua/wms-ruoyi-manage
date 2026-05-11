package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.ProductionException;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 异常上报业务对象 wms_production_exception
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ProductionException.class, reverseConvertGenerate = false)
public class ProductionExceptionBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "工单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long workOrderId;

    @NotNull(message = "异常类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer exceptionType;

    @NotBlank(message = "异常描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exceptionDesc;

    private LocalDateTime reportTime;

    private Integer handleStatus;

    private String handleResult;

    @NotBlank(message = "上报人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reportBy;

}
