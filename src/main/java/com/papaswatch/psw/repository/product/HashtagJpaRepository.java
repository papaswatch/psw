package com.papaswatch.psw.repository.product;

import com.papaswatch.psw.entity.product.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagJpaRepository extends JpaRepository<HashtagEntity, Long> {
    Optional<HashtagEntity> findByName(String name);
}
