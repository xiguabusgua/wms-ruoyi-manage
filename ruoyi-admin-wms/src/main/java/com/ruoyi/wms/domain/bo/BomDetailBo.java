package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.BomDetail;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * BOM明细业务对象 wms_bom_detail
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BomDetail.class, reverseConvertGenerate = false)
public class BomDetailBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "子件物料ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long itemId;

    @NotNull(message = "子件规格ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long skuId;

    @NotNull(message = "用量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal quantity;

    private String unit;

    private BigDecimal lossRate;

    private String position;

    private String isKeyPart;

    private String remark;

}
