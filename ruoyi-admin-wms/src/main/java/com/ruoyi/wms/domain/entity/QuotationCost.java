package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 成本核算明细对象 wms_quotation_cost
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_quotation_cost")
public class QuotationCost extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 报价单id
     */
    private Long quotationOrderId;

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 材料成本
     */
    private BigDecimal materialCost;

    /**
     * 人工成本
     */
    private BigDecimal laborCost;

    /**
     * 制造费用
     */
    private BigDecimal manufacturingCost;

    /**
     * 其他费用
     */
    private BigDecimal otherCost;

    /**
     * 总成本
     */
    private BigDecimal totalCost;

    /**
     * BOM版本号
     */
    private String bomVersion;

    /**
     * 工艺路线说明
     */
    private String processRoute;

    /**
     * 备注
     */
    private String remark;

}
