package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.QuotationCost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 成本核算明细视图对象 wms_quotation_cost
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = QuotationCost.class)
public class QuotationCostVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "报价单id")
    private Long quotationOrderId;

    @ExcelProperty(value = "规格id")
    private Long skuId;

    @ExcelProperty(value = "材料成本")
    private BigDecimal materialCost;

    @ExcelProperty(value = "人工成本")
    private BigDecimal laborCost;

    @ExcelProperty(value = "制造费用")
    private BigDecimal manufacturingCost;

    @ExcelProperty(value = "其他费用")
    private BigDecimal otherCost;

    @ExcelProperty(value = "总成本")
    private BigDecimal totalCost;

    @ExcelProperty(value = "BOM版本号")
    private String bomVersion;

    @ExcelProperty(value = "工艺路线说明")
    private String processRoute;

    @ExcelProperty(value = "备注")
    private String remark;

    private ItemSkuVo itemSku;

    private ItemVo item;

}
