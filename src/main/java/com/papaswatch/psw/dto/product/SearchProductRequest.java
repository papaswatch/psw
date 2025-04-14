package com.papaswatch.psw.dto.product;

import lombok.Data;

@Data
public class SearchProductRequest {
    private Integer page = 1;
    private Integer rows = 10;

    private String keyword;
    private ProductOrder order = ProductOrder.RECENT_CREATED;
}
