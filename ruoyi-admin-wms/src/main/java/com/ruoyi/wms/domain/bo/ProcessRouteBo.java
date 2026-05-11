package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.ProcessRoute;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 工艺路线主表业务对象 wms_process_route
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ProcessRoute.class, reverseConvertGenerate = false)
public class ProcessRouteBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "路线编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String routeCode;

    @NotBlank(message = "路线名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String routeName;

    @NotNull(message = "产品不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long productId;

    private Integer routeStatus;

    private String version;

    private String description;

    private String remark;

    /**
     * 工序步骤列表
     */
    private List<RouteStepBo> steps;

}
