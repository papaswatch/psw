package com.papaswatch.psw.entity.product;

import com.papaswatch.psw.entity.BaseEntity;
import com.papaswatch.psw.entity.UserInfoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.papaswatch.psw.config.Constant.DB.PAPAS_SCHEMA;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product", schema = PAPAS_SCHEMA)
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
    private Long liked = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfoEntity user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductHashtagMappEntity> productHashtagMapps = new ArrayList<>();

    private ProductEntity(
            String name,
            String contents,
            String brand,
            Integer stock,
            Integer price,
            UserInfoEntity user
    ) {
        this.name = name;
        this.contents = contents;
        this.brand = brand;
        this.stock = stock;
        this.price = price;
        this.user = user;
    }

    public static ProductEntity of(
            String name,
            String contents,
            String brand,
            Integer stock,
            Integer price,
            UserInfoEntity user
    ) {
        return new ProductEntity(name, contents, brand, stock, price, user);
    }

    public void addImages(List<ProductImageEntity> productImageEntities) {
        this.productImages.addAll(productImageEntities);
        productImageEntities.forEach(it -> it.setProductEntity(this));
    }

    public void addHashtagMapps(List<ProductHashtagMappEntity> productHashtagMapps) {
        this.productHashtagMapps.addAll(productHashtagMapps);
        productHashtagMapps.forEach(mapping -> mapping.setProduct(this));
    }
}
