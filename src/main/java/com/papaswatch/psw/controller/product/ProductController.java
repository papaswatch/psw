package com.papaswatch.psw.controller.product;

import com.papaswatch.psw.common.dto.Response;
import com.papaswatch.psw.dto.product.ProductInfo;
import com.papaswatch.psw.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    /**
     * 상품을 등록한다.
     */
    @PostMapping
    public Response<Boolean> addProduct(@RequestPart("productInfo") ProductInfo productInfo, @RequestPart("images") List<MultipartFile> imageFiles) {
        return Response.ok(productService.createProduct(productInfo, imageFiles));
    }

    @PutMapping("/edit")
    public void editProduct() {}

    @DeleteMapping("/delete")
    public void deleteProduct() {}
}
