package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.ruoyi.wms.domain.entity.Customer;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户管理视图对象 wms_customer
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Customer.class)
public class CustomerVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 客户编码
     */
    @ExcelProperty(value = "客户编码")
    private String customerCode;

    /**
     * 客户名称
     */
    @ExcelProperty(value = "客户名称")
    private String customerName;

    /**
     * 客户简称
     */
    @ExcelProperty(value = "客户简称")
    private String customerShortName;

    /**
     * 客户分类
     */
    @ExcelProperty(value = "客户分类")
    private Long customerId;

    /**
     * 分类名称
     */
    private String customerCategoryName;

    /**
     * 信用额度
     */
    @ExcelProperty(value = "信用额度")
    private BigDecimal creditLimit;

    /**
     * 账期(天)
     */
    @ExcelProperty(value = "账期(天)")
    private Integer paymentDays;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 联系人列表
     */
    private List<CustomerContactVo> contacts;

    /**
     * 地址列表
     */
    private List<CustomerAddressVo> addresses;

}
