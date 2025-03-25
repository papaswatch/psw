package com.papaswatch.psw.service;

import com.papaswatch.psw.dto.product.Product;
import com.papaswatch.psw.dto.product.ProductHashtag;
import com.papaswatch.psw.entity.product.ProductEntity;
import com.papaswatch.psw.exceptions.ApplicationException;
import com.papaswatch.psw.repository.product.ProductJpaRepository;
import com.papaswatch.psw.repository.product.ProductQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQuery productQuery;

    /**
     * 리스트에서 상품 단일 선택 시 ProductId를 받아서 상품의 정보를 response합니다.
     * @param productId
     * @return
     */
    @Transactional(readOnly = true)
    public Product getProductById(long productId) {
        ProductEntity productEntity = productJpaRepository.findById(productId).orElseThrow(ApplicationException::productNotFound);
        List<Long> productIds = new ArrayList<>();
        productIds.add(productId);
        List<ProductHashtag> productHashtagsBy = productQuery.findProductHashtagsBy(productIds);
        Map<Long, List<String>> map = productHashtagsBy.stream().collect(Collectors.groupingBy(ProductHashtag::getProductId, Collectors.mapping(ProductHashtag::getHashtag, Collectors.toList())));
        Product product = Product.fromProductEntity(productEntity);
        product.addHashtags(map.get(productId));

        return product;
    }

    public void getProductList() {
    }

    public void getDiscountProductItemList() {
    }

    public void getSearchTrend() {
    }

    public void getProductQNAList() {
    }

    public void getProductQNA(long productId) {
    }

    public void getReplyProductQNA(long productId) {

    }
}
