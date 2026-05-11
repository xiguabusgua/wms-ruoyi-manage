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
 * 采购订单对象 wms_purchase_order
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_purchase_order")
public class PurchaseOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 关联申请ID
     */
    private Long requisitionId;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 订单日期
     */
    private LocalDate orderDate;

    /**
     * 期望到货日期
     */
    private LocalDate expectArrivalDate;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态 0待审批 1已审批 2部分到货 3全部到货 4已关闭 5已取消
     */
    private Integer orderStatus;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货仓库ID
     */
    private Long warehouseId;

    /**
     * 备注
     */
    private String remark;

}
