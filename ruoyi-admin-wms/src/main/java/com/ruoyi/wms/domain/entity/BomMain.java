package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;

/**
 * BOM主表对象 wms_bom_main
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_bom_main")
public class BomMain extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * BOM编码
     */
    private String bomCode;

    /**
     * 版本号
     */
    private String bomVersion;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * BOM状态 0草稿 1正式 2失效
     */
    private Integer bomStatus;

    /**
     * 生效日期
     */
    private LocalDate effectiveDate;

    /**
     * 失效日期
     */
    private LocalDate expireDate;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

}
