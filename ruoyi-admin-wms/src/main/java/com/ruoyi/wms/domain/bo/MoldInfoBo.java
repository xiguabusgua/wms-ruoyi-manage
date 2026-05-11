package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.MoldInfo;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MoldInfo.class, reverseConvertGenerate = false)
public class MoldInfoBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "模具编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String moldCode;

    @NotBlank(message = "模具名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String moldName;

    private Integer moldType;

    private Long productId;

    private BigDecimal designLife;

    private BigDecimal currentLife;

    private BigDecimal totalStroke;

    private Integer moldStatus;

    private String storageLocation;

    private String manufacturer;

    private LocalDate purchaseDate;

    private String imageUrl;

    private String drawingUrl;

    private String remark;

}
