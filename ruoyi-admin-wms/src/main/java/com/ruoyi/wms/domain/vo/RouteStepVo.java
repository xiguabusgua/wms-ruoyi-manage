package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.RouteStep;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工序步骤视图对象 wms_route_step
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = RouteStep.class)
public class RouteStepVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "工艺路线ID")
    private Long routeId;

    @ExcelProperty(value = "工序序号")
    private Integer stepNo;

    @ExcelProperty(value = "工序名称")
    private String stepName;

    @ExcelProperty(value = "设备ID")
    private Long equipmentId;

    @ExcelProperty(value = "设备名称")
    private String equipmentName;

    @ExcelProperty(value = "标准工时(min)")
    private Integer standardTime;

    @ExcelProperty(value = "准备时间(min)")
    private Integer setupTime;

    @ExcelProperty(value = "人工成本")
    private BigDecimal laborCost;

    @ExcelProperty(value = "工序说明")
    private String description;

    @ExcelProperty(value = "备注")
    private String remark;

}
