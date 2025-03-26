package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.PageData;
import com.papaswatch.psw.common.dto.Response;
import com.papaswatch.psw.dto.LoginUserInfo;
import com.papaswatch.psw.dto.product.CreateProductRequest;
import com.papaswatch.psw.dto.product.Product;
import com.papaswatch.psw.dto.product.ReviewResponse;
import com.papaswatch.psw.dto.product.SearchProductRequest;
import com.papaswatch.psw.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.papaswatch.psw.config.Constant.USER.SESSION;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    /**
     * 상품을 등록한다.
     */
    @PostMapping
    public Response<Boolean> addProduct(HttpSession httpSession, @RequestPart("productInfo") CreateProductRequest productInfo, @RequestPart("images") List<MultipartFile> imageFiles) {
        LoginUserInfo user = (LoginUserInfo) httpSession.getAttribute(SESSION); // TODO: ArgumentResolver 로 처리할거임
        return Response.ok(productService.addProduct(user.getLoginId(), productInfo, imageFiles));
    }

    @GetMapping
    public Response<PageData<Product>> getProducts(@ModelAttribute SearchProductRequest req) {
        return Response.ok(productService.getProducts(req));
    }

    @PutMapping
    public void editProduct() {
    }

    @DeleteMapping
    public void deleteProduct() {
    }

    /**
     * 특정 물품 단건을 장바구니에 담습니다.
     *
     * @param productId
     * @param quantity
     * @param session
     * @return
     */
    @PostMapping("/cart/{productId}")
    public Response<Boolean> addCart(@PathVariable long productId, @RequestParam int quantity, HttpSession session) {
        boolean response = productService.addCart(productId, quantity, session);
        return Response.ok(response);
    }

    @DeleteMapping("/cart/{cartId}")
    public Response<Boolean> deleteCart(@PathVariable long cartId, HttpSession session) {
        boolean response = productService.removeCart(cartId, session);
        return Response.ok(response);
    }

    @PostMapping("/liked/{productId}")
    public Response<Boolean> addProductLiked(@PathVariable long productId, HttpSession session) {
        boolean response = productService.addProductLiked(productId, session);
        return Response.ok(response);
    }

    @DeleteMapping("/liked/{productId}")
    public Response<Boolean> deleteProductLiked(@PathVariable long productId, HttpSession session) {
        boolean response = productService.deleteProductLiked(productId, session);
        return Response.ok(response);
    }

    @PostMapping("/recent/{productId}")
    public Response<List<Long>> addRecentViewedProduct(@PathVariable long productId, HttpSession session) {
        List<Long> response = productService.addRecentViewedProduct(productId, session);
        return Response.ok(response);
    }

    @PostMapping("/review/{productId}")
    public Response<ReviewResponse> addProductReview(@PathVariable long productId, @RequestParam String productReview, @RequestParam int stars, HttpSession session) {
        ReviewResponse reviewResponse = productService.addProductReview(productId, productReview, stars, session);
        return Response.ok(reviewResponse);
    }

    @DeleteMapping("/review/{reviewId}")
    public Response<Boolean> deleteProductReview(@PathVariable long reviewId, HttpSession session) {
        boolean response = productService.deleteProductReview(reviewId, session);
        return Response.ok(response);
    }

    @PutMapping("/review/{reviewId}")
    public Response<Boolean> updateProductReview(@PathVariable long reviewId, @RequestParam String productReview, @RequestParam int stars, HttpSession session) {
        boolean response = productService.updateProductReview(reviewId, productReview, stars, session);
        return Response.ok(response);
    }
}
