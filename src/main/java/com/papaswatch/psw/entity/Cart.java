package com.papaswatch.psw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart", schema = "papas")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "product_count")
    private int productCount;

    public static Cart create(long userId, long productId, int productCount) {
        return new Cart(0L, userId, productId, productCount);
    }
}
