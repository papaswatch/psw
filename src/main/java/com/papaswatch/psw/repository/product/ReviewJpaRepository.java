package com.papaswatch.psw.repository.product;

import com.papaswatch.psw.entity.product.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
}
