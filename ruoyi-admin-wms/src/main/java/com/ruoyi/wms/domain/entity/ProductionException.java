package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 异常上报对象 wms_production_exception
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_production_exception")
public class ProductionException extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 工单ID
     */
    private Long workOrderId;

    /**
     * 异常类型 1-设备故障 2-缺料 3-质量 4-其他
     */
    private Integer exceptionType;

    /**
     * 异常描述
     */
    private String exceptionDesc;

    /**
     * 上报时间
     */
    private LocalDateTime reportTime;

    /**
     * 处理状态 0-未处理 1-处理中 2-已解决
     */
    private Integer handleStatus;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 上报人
     */
    private String reportBy;

}
