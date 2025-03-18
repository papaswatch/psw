package com.papaswatch.psw.repository.product;

import com.papaswatch.psw.entity.product.ProductHashtagMappEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHashtagMappJpaRepository extends JpaRepository<ProductHashtagMappEntity, Long> {
}
