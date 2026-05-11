package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * BOM版本记录对象 wms_bom_version
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_bom_version")
public class BomVersion extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * BOM主表ID
     */
    private Long bomId;

    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 变更说明
     */
    private String changeDesc;

    /**
     * 变更人
     */
    private String changeBy;

    /**
     * 变更时间
     */
    private LocalDateTime changeTime;

    /**
     * 备注
     */
    private String remark;

}
