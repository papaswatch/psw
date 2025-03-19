package com.papaswatch.psw.service.product;

import com.papaswatch.psw.dto.product.ProductInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    @Value("${images.product.dir}")
    private String productDir;

    public boolean createProduct(ProductInfo productInfo, List<MultipartFile> imageFiles) {
        log.debug("productInfo: {}", productInfo);
        for (MultipartFile imageFile : imageFiles) {
            log.debug("originalFilename: {}", imageFile.getOriginalFilename());
        }
        return true;
    }
}
