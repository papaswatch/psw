package com.papaswatch.psw.dto.product;

import com.papaswatch.psw.entity.product.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ReviewResponse {
    private String reviewContents;
    private int stars;

    public static ReviewResponse fromEntity(ReviewEntity entity) {
        return new ReviewResponse(entity.getReviewContents(), entity.getStars());
    }
}
