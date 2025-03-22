package com.papaswatch.psw.controller.product;

import com.papaswatch.psw.common.dto.Response;
import com.papaswatch.psw.dto.LoginUserInfo;
import com.papaswatch.psw.dto.product.ProductInfo;
import com.papaswatch.psw.service.product.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.papaswatch.psw.config.Constant.USER.SESSION;

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
    public Response<Boolean> addProduct(HttpSession httpSession, @RequestPart("productInfo") ProductInfo productInfo, @RequestPart("images") List<MultipartFile> imageFiles) {
        LoginUserInfo user = (LoginUserInfo) httpSession.getAttribute(SESSION); // TODO: ArgumentResolver 로 처리할거임
        return Response.ok(productService.addProduct(user.getLoginId(), productInfo, imageFiles));
    }

    @PutMapping("/edit")
    public void editProduct() {}

    @DeleteMapping("/delete")
    public void deleteProduct() {}
}
