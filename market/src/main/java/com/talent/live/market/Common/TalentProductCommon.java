package com.talent.live.market.Common;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class TalentProductCommon {
    private String id;

    private String categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

}
