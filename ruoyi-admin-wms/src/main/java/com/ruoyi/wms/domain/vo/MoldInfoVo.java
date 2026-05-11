package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.MoldInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = MoldInfo.class)
public class MoldInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "模具编号")
    private String moldCode;

    @ExcelProperty(value = "模具名称")
    private String moldName;

    @ExcelProperty(value = "模具类型")
    private Integer moldType;

    @ExcelProperty(value = "模具类型名")
    private String moldTypeName;

    @ExcelProperty(value = "适用产品ID")
    private Long productId;

    @ExcelProperty(value = "设计寿命(万次)")
    private BigDecimal designLife;

    @ExcelProperty(value = "当前寿命(万次)")
    private BigDecimal currentLife;

    @ExcelProperty(value = "累计冲次")
    private BigDecimal totalStroke;

    @ExcelProperty(value = "模具状态")
    private Integer moldStatus;

    @ExcelProperty(value = "模具状态名")
    private String moldStatusName;

    @ExcelProperty(value = "存放位置")
    private String storageLocation;

    @ExcelProperty(value = "制造商")
    private String manufacturer;

    @ExcelProperty(value = "购买日期")
    private LocalDate purchaseDate;

    @ExcelProperty(value = "图片URL")
    private String imageUrl;

    @ExcelProperty(value = "图纸URL")
    private String drawingUrl;

    @ExcelProperty(value = "备注")
    private String remark;

}
