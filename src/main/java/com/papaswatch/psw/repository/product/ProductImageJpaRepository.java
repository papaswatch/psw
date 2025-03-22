package com.papaswatch.psw.repository.product;

import com.papaswatch.psw.entity.product.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageJpaRepository extends JpaRepository<ProductImageEntity, Long> {
}
