package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.CustomerComplaint;
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
@AutoMapper(target = CustomerComplaint.class)
public class CustomerComplaintVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "投诉编号")
    private String complaintNo;

    @ExcelProperty(value = "客户ID")
    private Long customerId;

    @ExcelProperty(value = "客户名称")
    private String customerName;

    @ExcelProperty(value = "销售订单ID")
    private Long orderId;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "投诉类型")
    private String complaintType;

    @ExcelProperty(value = "投诉描述")
    private String complaintDesc;

    @ExcelProperty(value = "涉及数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "投诉日期")
    private LocalDate complaintDate;

    @ExcelProperty(value = "处理人")
    private String handler;

    @ExcelProperty(value = "处理状态(0待处理1处理中2已关闭)")
    private Integer handleStatus;

    @ExcelProperty(value = "处理结果")
    private String handleResult;

    @ExcelProperty(value = "关闭日期")
    private LocalDate closeDate;

    @ExcelProperty(value = "备注")
    private String remark;

}
