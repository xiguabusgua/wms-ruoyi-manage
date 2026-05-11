package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.IpqcInspection;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = IpqcInspection.class)
public class IpqcInspectionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "检验任务ID")
    private Long taskId;

    @ExcelProperty(value = "检验子类型(1首件2巡检3抽检)")
    private Integer inspectionSubType;

    @ExcelProperty(value = "工单ID")
    private Long workOrderId;

    @ExcelProperty(value = "工序/工位")
    private String stationName;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "抽样数量")
    private Integer sampleSize;

    @ExcelProperty(value = "不良数")
    private Integer defectQuantity;

    @ExcelProperty(value = "是否停线(Y/N)")
    private String isStopLine;

    @ExcelProperty(value = "停线原因")
    private String stopReason;

    @ExcelProperty(value = "检验员")
    private String inspector;

    @ExcelProperty(value = "检验时间")
    private LocalDateTime inspectTime;

    @ExcelProperty(value = "检验结论")
    private String conclusion;

    @ExcelProperty(value = "备注")
    private String remark;

}
