package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.ruoyi.wms.domain.entity.CustomerAddress;

import java.io.Serial;
import java.io.Serializable;

/**
 * 客户地址视图对象 wms_customer_address
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CustomerAddress.class)
public class CustomerAddressVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 客户ID
     */
    @ExcelProperty(value = "客户ID")
    private Long customerId;

    /**
     * 收货人
     */
    @ExcelProperty(value = "收货人")
    private String contactPerson;

    /**
     * 收货电话
     */
    @ExcelProperty(value = "收货电话")
    private String contactPhone;

    /**
     * 省
     */
    @ExcelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @ExcelProperty(value = "市")
    private String city;

    /**
     * 区
     */
    @ExcelProperty(value = "区")
    private String district;

    /**
     * 详细地址
     */
    @ExcelProperty(value = "详细地址")
    private String detailAddress;

    /**
     * 是否默认地址
     */
    @ExcelProperty(value = "是否默认地址")
    private String isDefault;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
