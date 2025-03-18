package com.papaswatch.psw.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_image")
@Entity
public class ProductImageEntity {
    @Id
    @Column(name = "img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    @Column(name = "name")
    private String name;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;
}
