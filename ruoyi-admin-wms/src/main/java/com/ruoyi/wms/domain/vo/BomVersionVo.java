package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.BomVersion;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BOM版本记录视图对象 wms_bom_version
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = BomVersion.class)
public class BomVersionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "BOM主表ID")
    private Long bomId;

    @ExcelProperty(value = "BOM编码")
    private String bomCode;

    @ExcelProperty(value = "版本号")
    private String versionNo;

    @ExcelProperty(value = "变更说明")
    private String changeDesc;

    @ExcelProperty(value = "变更人")
    private String changeBy;

    @ExcelProperty(value = "变更时间")
    private LocalDateTime changeTime;

    @ExcelProperty(value = "备注")
    private String remark;

}
