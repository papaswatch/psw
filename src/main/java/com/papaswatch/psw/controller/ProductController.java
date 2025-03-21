package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.Response;
import com.papaswatch.psw.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    /**
     * 특정 물품 단건을 장바구니에 담습니다.
     * @param productId
     * @param quantity
     * @param session
     * @return
     */
    @PostMapping("/cart/add/{productId}")
    public Response<Boolean> addCart(@PathVariable long productId, @RequestParam int quantity, HttpSession session) {
        boolean response = productService.addCart(productId, quantity, session);
        return Response.ok(response);
    }

    @DeleteMapping("/cart/delete/{cartId}")
    public Response<Boolean> deleteCart(@PathVariable long cartId, HttpSession session) {
        boolean response = productService.removeCart(cartId, session);
        return Response.ok(response);
    }

    @PostMapping("/liked/add/{productId}")
    public Response<Boolean> addProductLiked(@PathVariable long productId, HttpSession session) {
        boolean response = productService.addProductLiked(productId, session);
        return Response.ok(response);
    }

    @DeleteMapping("/liked/delete/{productId}")
    public Response<Boolean> deleteProductLiked(@PathVariable long productId, HttpSession session) {
        boolean response = productService.deleteProductLiked(productId, session);
        return Response.ok(response);
    }
}
