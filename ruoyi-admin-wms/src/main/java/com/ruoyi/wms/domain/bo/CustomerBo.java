package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.wms.domain.entity.Customer;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 客户管理业务对象 wms_customer
 *
 * @author zcc
 * @date 2024-07-16
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Customer.class, reverseConvertGenerate = false)
public class CustomerBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 客户编码
     */
    @NotBlank(message = "客户编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String customerCode;

    /**
     * 客户名称
     */
    @NotBlank(message = "客户名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String customerName;

    /**
     * 客户分类
     */
    @NotNull(message = "客户分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long customerId;

    /**
     * 状态
     */
    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 联系人列表
     */
    private List<CustomerContactBo> contacts;

    /**
     * 地址列表
     */
    private List<CustomerAddressBo> addresses;


}
