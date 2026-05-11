package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.wms.domain.entity.CustomerContact;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 客户联系人业务对象 wms_customer_contact
 *
 * @author zcc
 * @date 2024-07-16
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CustomerContact.class, reverseConvertGenerate = false)
public class CustomerContactBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(groups = { EditGroup.class })
    private Long id;

    /**
     * 客户ID
     */
    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    /**
     * 联系人姓名
     */
    @NotBlank(message = "联系人姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contactName;


}
