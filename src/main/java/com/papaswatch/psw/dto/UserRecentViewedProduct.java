package com.papaswatch.psw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserRecentViewedProduct {
    private long userId;
    private List<ProductRecentViewed> productRecentViewed;
}