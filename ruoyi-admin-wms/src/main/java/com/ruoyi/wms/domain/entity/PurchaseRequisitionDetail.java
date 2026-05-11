package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 采购申请明细对象 wms_purchase_requisition_detail
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_purchase_requisition_detail")
public class PurchaseRequisitionDetail extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 采购申请ID
     */
    private Long requisitionId;

    /**
     * 物料ID
     */
    private Long itemId;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 需求数量
     */
    private BigDecimal quantity;

    /**
     * 预估单价
     */
    private BigDecimal unitPrice;

    /**
     * 预估金额
     */
    private BigDecimal amount;

    /**
     * 需求日期
     */
    private LocalDate demandDate;

    /**
     * 建议供应商ID
     */
    private Long supplierId;

    /**
     * 备注
     */
    private String remark;

}
