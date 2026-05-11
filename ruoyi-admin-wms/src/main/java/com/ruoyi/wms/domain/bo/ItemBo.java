package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.wms.domain.entity.Item;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Item.class, reverseConvertGenerate = false)
public class ItemBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 主键集合
     */
    private List<Long> ids;

    /**
     * 物料编码
     */
    @NotBlank(message = "物料编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String itemCode;

    /**
     * 物料名称
     */
    @NotBlank(message = "物料名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String itemName;

    /**
     * 物料分类
     */
    @NotBlank(message = "物料分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String itemCategory;

    /**
     * 规格型号
     */
    private String specModel;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 安全库存
     */
    private BigDecimal safetyStock;

    /**
     * 计价方式（标准成本/移动平均）
     */
    @NotBlank(message = "计价方式不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pricingMethod;

    /**
     * 品牌
     */
    private Long itemBrand;


    /**
     * 备注
     */
    private String remark;

    private List<ItemSkuBo> sku;

}
