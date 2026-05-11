package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.ProcessRoute;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 工艺路线主表视图对象 wms_process_route
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = ProcessRoute.class)
public class ProcessRouteVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "路线编码")
    private String routeCode;

    @ExcelProperty(value = "路线名称")
    private String routeName;

    @ExcelProperty(value = "产品ID")
    private Long productId;

    @ExcelProperty(value = "产品名称")
    private String productName;

    @ExcelProperty(value = "路线状态")
    private Integer routeStatus;

    @ExcelProperty(value = "路线状态名称")
    private String routeStatusName;

    @ExcelProperty(value = "版本")
    private String version;

    @ExcelProperty(value = "描述")
    private String description;

    @ExcelProperty(value = "备注")
    private String remark;

    private List<RouteStepVo> steps;

}
