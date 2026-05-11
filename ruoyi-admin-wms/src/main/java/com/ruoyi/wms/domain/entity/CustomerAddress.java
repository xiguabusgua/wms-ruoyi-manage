package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户收货地址对象 wms_customer_address
 *
 * @author zcc
 * @date 2024-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_customer_address")
public class CustomerAddress extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 关联客户ID
     */
    private Long customerId;

    /**
     * 收货人
     */
    private String contactPerson;

    /**
     * 收货电话
     */
    private String contactPhone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 是否默认地址（Y/N）
     */
    private String isDefault;

    /**
     * 备注
     */
    private String remark;

}
