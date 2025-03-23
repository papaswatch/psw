package com.papaswatch.psw.repository;

import com.papaswatch.psw.entity.ProductLikedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikedRepository extends JpaRepository<ProductLikedEntity, Long> {
}
