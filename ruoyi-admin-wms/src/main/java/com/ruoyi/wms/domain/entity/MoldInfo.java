package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_mold_info")
public class MoldInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String moldCode;

    private String moldName;

    private Integer moldType;

    private Long productId;

    private BigDecimal designLife;

    private BigDecimal currentLife;

    private BigDecimal totalStroke;

    private Integer moldStatus;

    private String storageLocation;

    private String manufacturer;

    private LocalDate purchaseDate;

    private String imageUrl;

    private String drawingUrl;

    private String remark;

}
