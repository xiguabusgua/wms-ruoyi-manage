package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 工艺路线主表对象 wms_process_route
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_process_route")
public class ProcessRoute extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 路线编码
     */
    private String routeCode;

    /**
     * 路线名称
     */
    private String routeName;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 路线状态 0草稿 1启用 2停用
     */
    private Integer routeStatus;

    /**
     * 版本
     */
    private String version;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

}
