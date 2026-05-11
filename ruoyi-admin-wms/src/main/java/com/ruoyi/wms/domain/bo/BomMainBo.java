package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.BomMain;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * BOM主表业务对象 wms_bom_main
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BomMain.class, reverseConvertGenerate = false)
public class BomMainBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "BOM编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bomCode;

    private String bomVersion;

    @NotNull(message = "产品不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long productId;

    private Integer bomStatus;

    private LocalDate effectiveDate;

    private LocalDate expireDate;

    private String description;

    private String remark;

    /**
     * BOM明细列表
     */
    private List<BomDetailBo> details;

}
