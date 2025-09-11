package com.wisps.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodDto {
    private Long id;
    private String name;
    private String desc;
    private BigDecimal price;
}
