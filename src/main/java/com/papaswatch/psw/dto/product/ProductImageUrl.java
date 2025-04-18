package com.papaswatch.psw.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class ProductImageUrl {
    private Long id;
    private String url;
}
