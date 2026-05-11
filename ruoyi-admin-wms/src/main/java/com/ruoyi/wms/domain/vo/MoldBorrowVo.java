package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.MoldBorrow;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = MoldBorrow.class)
public class MoldBorrowVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "模具ID")
    private Long moldId;

    @ExcelProperty(value = "模具编号")
    private String moldCode;

    @ExcelProperty(value = "模具名称")
    private String moldName;

    @ExcelProperty(value = "借用工单号")
    private String borrowNo;

    @ExcelProperty(value = "借用人")
    private String borrower;

    @ExcelProperty(value = "借用时间")
    private LocalDateTime borrowDate;

    @ExcelProperty(value = "归还时间")
    private LocalDateTime returnDate;

    @ExcelProperty(value = "借用状态")
    private Integer borrowStatus;

    @ExcelProperty(value = "借用状态名")
    private String borrowStatusName;

    @ExcelProperty(value = "关联工单ID")
    private Long workOrderId;

    @ExcelProperty(value = "归还检查结果")
    private String returnCheckResult;

    @ExcelProperty(value = "备注")
    private String remark;

}
