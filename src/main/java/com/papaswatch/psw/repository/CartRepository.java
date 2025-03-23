package com.papaswatch.psw.repository;

import com.papaswatch.psw.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
