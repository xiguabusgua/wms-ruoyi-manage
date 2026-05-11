package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.BomMain;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * BOM主表视图对象 wms_bom_main
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = BomMain.class)
public class BomMainVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "BOM编码")
    private String bomCode;

    @ExcelProperty(value = "版本号")
    private String bomVersion;

    @ExcelProperty(value = "产品ID")
    private Long productId;

    @ExcelProperty(value = "产品名称")
    private String productName;

    @ExcelProperty(value = "BOM状态")
    private Integer bomStatus;

    @ExcelProperty(value = "BOM状态名称")
    private String bomStatusName;

    @ExcelProperty(value = "生效日期")
    private LocalDate effectiveDate;

    @ExcelProperty(value = "失效日期")
    private LocalDate expireDate;

    @ExcelProperty(value = "描述")
    private String description;

    @ExcelProperty(value = "备注")
    private String remark;

    private List<BomDetailVo> details;

}
