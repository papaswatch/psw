package com.papaswatch.psw.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@ToString
@Getter
public class Product {
    private final Long productId;
    private final String name;
    private final String contents;
    private final String brand;
    private final Integer stock;
    private final Integer price;
    private final Long liked;
    private final String seller;
    private final String imgFilePath;
    private final String imgHashName;
    private final String imgExtension;

    private List<String> hashtags;

    @QueryProjection
    public Product(Long productId, String name, String contents, String brand,
                   Integer stock, Integer price, Long liked, String seller, String imgFilePath, String imgHashName, String imgExtension) {
        this.productId = productId;
        this.name = name;
        this.contents = contents;
        this.brand = brand;
        this.stock = stock;
        this.price = price;
        this.liked = liked;
        this.seller = seller;
        this.imgFilePath = imgFilePath;
        this.imgHashName = imgHashName;
        this.imgExtension = imgExtension;
    }

    public void addHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }
}
