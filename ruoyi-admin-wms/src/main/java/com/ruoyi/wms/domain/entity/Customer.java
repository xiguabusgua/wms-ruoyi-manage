package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 客户档案对象 wms_customer
 *
 * @author zcc
 * @date 2024-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_customer")
public class Customer extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户简称
     */
    private String customerShortName;

    /**
     * 关联客户分类ID
     */
    private Long customerId;

    /**
     * 信用额度
     */
    private BigDecimal creditLimit;

    /**
     * 账期天数
     */
    private Integer paymentDays;

    /**
     * 状态（0停用 1启用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
