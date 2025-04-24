package com.papaswatch.psw.repository.product;

import com.papaswatch.psw.entity.product.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageJpaRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findByImgIdIn(List<Long> imgIds);
}
