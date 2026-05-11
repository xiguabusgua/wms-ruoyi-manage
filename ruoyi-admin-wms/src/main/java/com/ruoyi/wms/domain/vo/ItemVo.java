package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.Item;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Item.class)
public class ItemVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码")
    private String itemCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称")
    private String itemName;

    /**
     * 物料分类
     */
    @ExcelProperty(value = "物料分类")
    private String itemCategory;

    /**
     * 规格型号
     */
    @ExcelProperty(value = "规格型号")
    private String specModel;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 安全库存
     */
    @ExcelProperty(value = "安全库存")
    private BigDecimal safetyStock;

    /**
     * 计价方式（标准成本/移动平均）
     */
    @ExcelProperty(value = "计价方式")
    private String pricingMethod;

    /**
     * 品牌
     */
    @ExcelProperty(value = "品牌")
    private Long itemBrand;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 类别
     */
    private ItemCategoryVo itemCategoryInfo;
}
