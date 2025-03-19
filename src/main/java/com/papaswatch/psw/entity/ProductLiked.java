package com.papaswatch.psw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Table(name = "product_liked", schema = "papas")
public class ProductLiked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_liked_id")
    private long productLikedId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "product_id")
    private long productId;

    public static ProductLiked create(long userId, long productId) {
        return new ProductLiked(0L, userId, productId);
    }
}
