package com.papaswatch.psw.dto.product;

import lombok.Data;

@Data
public class SearchProductRequest {
    private Integer page;
    private Integer rows;

    private String keyword;
    private ProductOrder order;
}
