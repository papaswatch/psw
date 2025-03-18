package com.papaswatch.psw.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_hashtag_mapp")
@Entity
public class ProductHashtagMappEntity {
    @Id
    @Column(name = "product_hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private HashtagEntity hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;
}
