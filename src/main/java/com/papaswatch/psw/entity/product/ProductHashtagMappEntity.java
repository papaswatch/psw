package com.papaswatch.psw.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.papaswatch.psw.config.Constant.DB.PAPAS_SCHEMA;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_hashtag_mapp", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "hashtag_id"})
}, schema = PAPAS_SCHEMA)
@Entity
public class ProductHashtagMappEntity {
    @Id
    @Column(name = "product_hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private HashtagEntity hashtag;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    private ProductHashtagMappEntity(HashtagEntity hashtag, ProductEntity product) {
        this.hashtag = hashtag;
        this.product = product;
    }

    public static ProductHashtagMappEntity of(HashtagEntity hashtag, ProductEntity product) {
        return new ProductHashtagMappEntity(hashtag, product);
    }

}
