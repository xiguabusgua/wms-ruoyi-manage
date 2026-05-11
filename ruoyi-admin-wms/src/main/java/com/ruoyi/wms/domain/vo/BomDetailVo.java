package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.BomDetail;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * BOM明细视图对象 wms_bom_detail
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = BomDetail.class)
public class BomDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "BOM主表ID")
    private Long bomId;

    @ExcelProperty(value = "子件物料ID")
    private Long itemId;

    @ExcelProperty(value = "物料名称")
    private String itemName;

    @ExcelProperty(value = "子件规格ID")
    private Long skuId;

    @ExcelProperty(value = "规格名称")
    private String skuName;

    @ExcelProperty(value = "用量")
    private BigDecimal quantity;

    @ExcelProperty(value = "单位")
    private String unit;

    @ExcelProperty(value = "损耗率(%)")
    private BigDecimal lossRate;

    @ExcelProperty(value = "位号")
    private String position;

    @ExcelProperty(value = "是否关键件")
    private String isKeyPart;

    @ExcelProperty(value = "备注")
    private String remark;

}
