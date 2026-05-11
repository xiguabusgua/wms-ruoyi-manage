package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入库单对象 wms_receipt_order
 *
 * @author zcc
 * @date 2024-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_receipt_order")
public class ReceiptOrder extends BaseOrder {
    /**
     * 仓库id
     */
    private Long warehouseId;

    /**
     * 入库类型
     */
    private Long optType;
    /**
     * 业务订单号
     */
    private String bizOrderNo;
    /**
     * 供应商
     */
    private Long merchantId;

    /**
     * 入库类型(1采购入库2生产入库3退货入库4其他入库)
     */
    private Integer receiptType;
    /**
     * 来源单据ID
     */
    private Long sourceOrderId;
    /**
     * 来源单据号
     */
    private String sourceOrderNo;

}
