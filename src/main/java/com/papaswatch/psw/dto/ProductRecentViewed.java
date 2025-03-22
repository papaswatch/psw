package com.papaswatch.psw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ProductRecentViewed {
    private long productId;
    private String productName;
    private long productImageId;
    private String productImageSavedPath;
}
