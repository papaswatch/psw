package com.papaswatch.psw.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductHashtag {
    private final Long productId;
    private final String hashtag;

    @QueryProjection
    public ProductHashtag(Long productId, String hashtag) {
        this.productId = productId;
        this.hashtag = hashtag;
    }
}
