package com.papaswatch.psw.repository;

import com.papaswatch.psw.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
