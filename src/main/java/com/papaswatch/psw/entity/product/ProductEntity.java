package com.papaswatch.psw.entity.product;

import com.papaswatch.psw.entity.BaseEntity;
import com.papaswatch.psw.entity.UserInfoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity
public class ProductEntity extends BaseEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "contents")
    private String contents;

    @Column(name = "brand")
    private String brand;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "price")
    private Integer price;

    @Column(name = "liked")
    private Long liked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfoEntity user;

    @OneToMany(mappedBy = "product")
    private List<ProductImageEntity> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductHashtagMappEntity> productHashtagMapps = new ArrayList<>();
}
