package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * BOM明细对象 wms_bom_detail
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_bom_detail")
public class BomDetail extends BaseEntity {

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
     * 子件物料ID
     */
    private Long itemId;

    /**
     * 子件规格ID
     */
    private Long skuId;

    /**
     * 用量
     */
    private BigDecimal quantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 损耗率
     */
    private BigDecimal lossRate;

    /**
     * 位号
     */
    private String position;

    /**
     * 是否关键件 Y/N
     */
    private String isKeyPart;

    /**
     * 备注
     */
    private String remark;

}
