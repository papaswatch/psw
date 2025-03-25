package com.papaswatch.psw.repository.product;

import com.papaswatch.psw.dto.product.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.papaswatch.psw.entity.QUserInfoEntity.userInfoEntity;
import static com.papaswatch.psw.entity.product.QHashtagEntity.hashtagEntity;
import static com.papaswatch.psw.entity.product.QProductEntity.productEntity;
import static com.papaswatch.psw.entity.product.QProductHashtagMappEntity.productHashtagMappEntity;
import static com.papaswatch.psw.entity.product.QProductImageEntity.productImageEntity;

@RequiredArgsConstructor
@Component
public class ProductQuery {

    private final JPAQueryFactory query;

    /**
     * 상품 리스트 검색을 `Product` dto 로 반환하여 응답.
     */
    public Page<Product> findProductsPageBy(SearchProductRequest request, Pageable pageable) {
        List<Product> products = findProductsBy(request, pageable);
        Long cnt = findProductCountBy(request);
        return new PageImpl<>(products, pageable, cnt);
    }

    /**
     * 상품 리스트를 불러올 때 사용한다.
     */
    public List<Product> findProductsBy(SearchProductRequest request, Pageable pageable) {
        return query.select(new QProduct(productEntity.productId, productEntity.name, productEntity.contents, productEntity.brand, productEntity.stock, productEntity.price, productEntity.liked, userInfoEntity.name, productImageEntity.filePath, productImageEntity.hashName, productImageEntity.extension))
                .from(productEntity)
                .join(userInfoEntity).on(userInfoEntity.userId.eq(productEntity.user.userId))
                .join(productImageEntity).on(productEntity.productId.eq(productImageEntity.product.productId))
                .where(keywordContains(request.getKeyword()), productImageEntity.isThumbnail.isTrue())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order(request.getOrder()))
                .fetch();
    }

    /**
     * 상품 리스트의 해시태그를 가져온다.
     */
    public List<ProductHashtag> findProductHashtagsBy(List<Long> productIds) {
        return query.select(new QProductHashtag(productEntity.productId, hashtagEntity.name))
                .from(productEntity)
                .join(productHashtagMappEntity).on(productEntity.productId.eq(productHashtagMappEntity.product.productId))
                .join(hashtagEntity).on(productHashtagMappEntity.hashtag.hashtagId.eq(hashtagEntity.hashtagId))
                .where(productEntity.productId.in(productIds))
                .fetch();
    }


    /////////////////////////////////////////////////////////////////////////
    // private method area..
    /**
     * 상품 검색 개수를 구할 때 사용한다.
     * */
    private Long findProductCountBy(SearchProductRequest request) {
        Long cnt = query.select(productEntity.count())
                .from(productEntity)
                .join(userInfoEntity).on(userInfoEntity.userId.eq(productEntity.user.userId))
                .join(productImageEntity).on(productEntity.productId.eq(productImageEntity.product.productId))
                .where(keywordContains(request.getKeyword()), productImageEntity.isThumbnail.isTrue())
                .fetchOne();
        if (cnt == null) return 0L;
        return cnt;
    }

    private BooleanExpression keywordContains(String keyword) {
        if (!StringUtils.hasText(keyword)) return null;
        return productEntity.name.contains(keyword)
                .or(productEntity.brand.contains(keyword));
    }

    private OrderSpecifier<?> order(ProductOrder order) {
        switch (order) {
            case RECENT_CREATED:
                return productEntity.createdAt.desc();
            case HIGH_LIKED:
                return productEntity.liked.desc();
            case HIGH_PRICE:
                return productEntity.price.desc();
            case OLD_CREATED:
                return productEntity.createdAt.asc();
            case LOW_LIKED:
                return productEntity.liked.asc();
            case LOW_PRICE:
                return productEntity.price.asc();
            default:
                throw new IllegalArgumentException();
        }
    }
}
