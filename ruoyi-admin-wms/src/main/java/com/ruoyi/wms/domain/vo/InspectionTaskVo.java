package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.InspectionTask;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@AutoMapper(target = InspectionTask.class)
public class InspectionTaskVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID")
    private Long id;

    @ExcelProperty(value = "任务编号")
    private String taskNo;

    @ExcelProperty(value = "检验类型")
    private Integer inspectionType;

    @ExcelProperty(value = "来源单据ID")
    private Long sourceOrderId;

    @ExcelProperty(value = "来源单据号")
    private String sourceOrderNo;

    @ExcelProperty(value = "物料ID")
    private Long productId;

    @ExcelProperty(value = "物料名称")
    private String productName;

    @ExcelProperty(value = "批次号")
    private String batchNo;

    @ExcelProperty(value = "抽检数量")
    private BigDecimal quantity;

    @ExcelProperty(value = "任务状态")
    private Integer taskStatus;

    @ExcelProperty(value = "检验员")
    private String inspector;

    @ExcelProperty(value = "检验时间")
    private LocalDateTime inspectTime;

    @ExcelProperty(value = "检验结论")
    private String conclusion;

    @ExcelProperty(value = "备注")
    private String remark;

    private List<InspectionItemVo> items;

}
