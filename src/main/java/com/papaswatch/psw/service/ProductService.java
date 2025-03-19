package com.papaswatch.psw.service;

import com.papaswatch.psw.entity.Cart;
import com.papaswatch.psw.repository.CartRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final UserService userService;
    private final CartRepository cartRepository;

    public boolean addCart(long productId, int quantity, HttpSession session) {
        long userId = userService.getUserId(session);
        cartRepository.save(Cart.create(userId, productId, quantity));
        return true;
    }

}
