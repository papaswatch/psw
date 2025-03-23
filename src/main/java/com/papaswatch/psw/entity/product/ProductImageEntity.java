package com.papaswatch.psw.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.papaswatch.psw.config.Constant.DB.PAPAS_SCHEMA;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_image", schema = PAPAS_SCHEMA)
@Entity
public class ProductImageEntity {
    @Id
    @Column(name = "img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "hash_name")
    private String hashName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "extension")
    private String extension;

    @Column(name = "is_thumbnail")
    private Boolean isThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private ProductImageEntity(String originName, String hashName, String filePath, String extension, Boolean isThumbnail) {
        this.originName = originName;
        this.hashName = hashName;
        this.filePath = filePath;
        this.extension = extension;
        this.isThumbnail = isThumbnail;
    }

    public static ProductImageEntity createBy(String originName, String hashName, String filePath, String extension, Boolean isThumbnail) {
        return new ProductImageEntity(originName, hashName, filePath, extension, isThumbnail);
    }

    public void setProductEntity(ProductEntity product) {
        this.product = product;
    }
}
