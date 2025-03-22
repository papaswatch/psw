package com.papaswatch.psw.repository.product;

import com.papaswatch.psw.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
