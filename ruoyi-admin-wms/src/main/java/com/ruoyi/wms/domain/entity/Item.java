package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_item")
public class Item extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 物料分类
     */
    private String itemCategory;

    /**
     * 规格型号
     */
    private String specModel;

    /**
     * 单位
     */
    private String unit;

    /**
     * 安全库存
     */
    private BigDecimal safetyStock;

    /**
     * 计价方式（标准成本/移动平均）
     */
    private String pricingMethod;

    /**
     * 品牌
     */
    private Long itemBrand;

    /**
     * 备注
     */
    private String remark;


}
