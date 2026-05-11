package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户联系人对象 wms_customer_contact
 *
 * @author zcc
 * @date 2024-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_customer_contact")
public class CustomerContact extends BaseEntity {

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
     * 联系人姓名
     */
    private String contactName;

    /**
     * 职务
     */
    private String position;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 座机
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否主要联系人（Y/N）
     */
    private String isPrimary;

    /**
     * 备注
     */
    private String remark;

}
