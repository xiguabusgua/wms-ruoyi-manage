package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import com.ruoyi.wms.domain.entity.MoldCategory;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MoldCategory.class)
public class MoldCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "父分类ID")
    private Long parentId;

    @ExcelProperty(value = "分类名称")
    private String categoryName;

    @ExcelProperty(value = "排序")
    private Long orderNum;

    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=停用,1=正常")
    private String status;

    @TableField(exist = false)
    private List<MoldCategoryVo> children = new ArrayList<>();

}
