package com.ruoyi.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.ruoyi.wms.domain.entity.CustomerContact;

import java.io.Serial;
import java.io.Serializable;

/**
 * 客户联系人视图对象 wms_customer_contact
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CustomerContact.class)
public class CustomerContactVo implements Serializable {

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
     * 联系人姓名
     */
    @ExcelProperty(value = "联系人姓名")
    private String contactName;

    /**
     * 职务
     */
    @ExcelProperty(value = "职务")
    private String position;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String mobile;

    /**
     * 座机
     */
    @ExcelProperty(value = "座机")
    private String tel;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 是否主要联系人
     */
    @ExcelProperty(value = "是否主要联系人")
    private String isPrimary;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
