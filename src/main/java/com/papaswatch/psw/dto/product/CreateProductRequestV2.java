package com.papaswatch.psw.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductRequestV2 {
    private String name;
    private String brand;
    private Integer price;
    private Integer stock;
    private String description;
    private List<Long> imageIds;
    private List<String> hashtags;
}
