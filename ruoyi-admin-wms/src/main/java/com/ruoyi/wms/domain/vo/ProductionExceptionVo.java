package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.ProductionException;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 异常上报视图对象 wms_production_exception
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = ProductionException.class)
public class ProductionExceptionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "工单ID")
    private Long workOrderId;

    @ExcelProperty(value = "工单号")
    private String workOrderNo;

    @ExcelProperty(value = "异常类型")
    private Integer exceptionType;

    @ExcelProperty(value = "异常类型名称")
    private String exceptionTypeName;

    @ExcelProperty(value = "异常描述")
    private String exceptionDesc;

    @ExcelProperty(value = "上报时间")
    private LocalDateTime reportTime;

    @ExcelProperty(value = "处理状态")
    private Integer handleStatus;

    @ExcelProperty(value = "处理状态名称")
    private String handleStatusName;

    @ExcelProperty(value = "处理结果")
    private String handleResult;

    @ExcelProperty(value = "上报人")
    private String reportBy;

}
