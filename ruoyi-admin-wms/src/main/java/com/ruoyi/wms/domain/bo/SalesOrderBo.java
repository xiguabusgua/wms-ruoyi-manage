package com.ruoyi.wms.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.wms.domain.entity.SalesOrder;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 销售订单业务对象 wms_sales_order
 *
 * @author zcc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SalesOrder.class, reverseConvertGenerate = false)
public class SalesOrderBo {

    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotBlank(message = "订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderNo;

    @NotNull(message = "客户不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long customerId;

    @NotNull(message = "订单日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime orderDate;

    @NotNull(message = "交货日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDate deliveryDate;

    private BigDecimal totalQuantity;

    private BigDecimal totalAmount;

    private Integer orderStatus;

    private Long quotationOrderId;

    private String remark;

    /**
     * 订单明细列表
     */
    private List<SalesOrderDetailBo> details;

}
