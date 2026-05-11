package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_mold_borrow")
public class MoldBorrow extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Long moldId;

    private String borrowNo;

    private String borrower;

    private LocalDateTime borrowDate;

    private LocalDateTime returnDate;

    private Integer borrowStatus;

    private Long workOrderId;

    private String returnCheckResult;

    private String remark;

}
