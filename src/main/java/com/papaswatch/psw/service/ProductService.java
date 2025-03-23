package com.papaswatch.psw.service;

import com.papaswatch.psw.dto.ProductRecentViewed;
import com.papaswatch.psw.entity.CartEntity;
import com.papaswatch.psw.entity.ProductLikedEntity;
import com.papaswatch.psw.entity.product.ProductEntity;
import com.papaswatch.psw.entity.product.ProductImageEntity;
import com.papaswatch.psw.exceptions.ApplicationException;
import com.papaswatch.psw.repository.CartRepository;
import com.papaswatch.psw.repository.ProductLikedRepository;
import com.papaswatch.psw.repository.product.ProductJpaRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final UserService userService;
    private final CartRepository cartRepository;
    private final ProductLikedRepository productLikedRepository;
    private final ProductJpaRepository productRepository;

    // 각 유저별 최근 본 상품을 저장하는 Map, Key는 유저의 key값입니다.
    ConcurrentHashMap<Long, Queue<ProductRecentViewed>> userRecentViewedProduct = new ConcurrentHashMap<>();

    // 최근 본 상품의 저장될 최대 갯수 지정
    int MAX_RECENT_VIEWED_QUANTITY = 5;

    /**
     * 장바구니에 추가
     *
     * @param productId
     * @param quantity
     * @param session
     * @return
     */
    @Transactional
    public boolean addCart(long productId, int quantity, HttpSession session) {
        long userId = userService.getUserId(session);
        try {
            cartRepository.save(CartEntity.create(userId, productId, quantity));
            return true;
        } catch (Exception e) {
            log.error("failed to add cart to user {}", userId);
            return false;
        }
    }

    /**
     * 장바구니에서 제거
     *
     * @param cartId
     * @param session
     * @return
     */
    @Transactional
    public boolean removeCart(long cartId, HttpSession session) {
        CartEntity cartEntity = cartRepository.findById(cartId).orElseThrow(ApplicationException::CartDataNotFound);
        long userIdInCart = cartEntity.getUserId();
        long currentUserId = userService.getUserId(session);
        // 현재 접속한 유저의 아이디와 장바구니 정보에 저장된 유저 아이디가 같으면 해당 장바구니데이터를 삭제합니다
        if (userIdInCart == currentUserId) {
            try {
                cartRepository.delete(cartEntity);
                return true;
            } catch (Exception e) {
                log.error("failed to remove cart {}", userIdInCart);
                return false;
            }
        }
        throw new ApplicationException("Cant delete cart due to user is not matched");
    }

    /**
     * 찜목록에 추가
     *
     * @param productId
     * @param session
     * @return
     */
    @Transactional
    public boolean addProductLiked(long productId, HttpSession session) {
        long userId = userService.getUserId(session);
        try {
            productLikedRepository.save(ProductLikedEntity.create(userId, productId));
            return true;
        } catch (Exception e) {
            log.error("failed to add product liked to user {}", userId);
            return false;
        }
    }

    /**
     * 찜목록에서 삭제
     *
     * @param productId
     * @param session
     * @return
     */
    @Transactional
    public boolean deleteProductLiked(long productId, HttpSession session) {
        long currentUserId = userService.getUserId(session);
        ProductLikedEntity productLikedEntity = productLikedRepository.findById(productId).orElseThrow(ApplicationException::ProductLikedNotFound);

        if (productLikedEntity.getUserId() == currentUserId) {
            try {
                productLikedRepository.delete(productLikedEntity);
                return true;
            } catch (Exception e) {
                log.error("failed to remove product liked to user {}", currentUserId);
                return false;
            }
        }
        return false;
    }

    /**
     * 최근 본 상품 추가
     * @param productId
     * @param session
     * @return
     */
    @Transactional
    public List<ProductRecentViewed> addRecentViewedProduct(long productId, HttpSession session) {
        long loginUserId = userService.getUserId(session);

        // 유저의 최근 본 상품을 저장하는 Queue, 최대 5개까지 저장합니다.
        Queue<ProductRecentViewed> recentViewedQueue = new ArrayDeque<>();

        // 현재 접속중인 유저의 id의 Queue 정보를 가져옵니다. 없으면 새로운 Queue를 넣어줍니다.
        Queue<ProductRecentViewed> userRecentViewedProductQueue = userRecentViewedProduct.computeIfAbsent(loginUserId, user -> new ArrayDeque<>());
        log.info("check user :: {} recent viewed :: {}", loginUserId, userRecentViewedProductQueue.toString());

        //최근 본 상품의 크기가 5 이상이면 우선 한개 빼고 시작
        if (userRecentViewedProductQueue.size() >= MAX_RECENT_VIEWED_QUANTITY) {
            userRecentViewedProductQueue.poll();
        }

        // 추가 될 최근 본 상품에 대한 정보를 생성합니다.
        ProductRecentViewed recentViewedProduct = createRecentViewedProduct(productId);

        userRecentViewedProductQueue.offer(recentViewedProduct);

        return createRecentViewedProductResponse(userRecentViewedProductQueue);
    }

    /**
     * 유저의 최근 본 상품 Response 데이터를 생성합니다.
     * @param recentViewedQueue
     * @return
     */
    public List<ProductRecentViewed> createRecentViewedProductResponse(Queue<ProductRecentViewed> recentViewedQueue) {
        List<ProductRecentViewed> response = new ArrayList<>(recentViewedQueue);
        Collections.reverse(response);
        log.info("check recent viewed response :: {}", response.toString());
        return response;
    }

    /**
     * 최근 본 상품 객체를 생성하여 response합니다.
     * @param productId
     * @return
     */
    @Transactional(readOnly = true)
    public ProductRecentViewed createRecentViewedProduct(long productId) {
        // 최근 본 상품에 추가할 상품의 정보를 가져옵니다.
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(ApplicationException::ProductNotFound);
        
        String productName = productEntity.getName();

        // 첫번째 이미지 정보를 가져옵니다.(대표이미지) TODO NOTE 대표이미지에 대한 설정이 필요하지 않은가?(PSW-0000-143)
        ProductImageEntity representativeImage = productEntity.getProductImages().getFirst();
        Long productRepresentativeImage = representativeImage.getImgId();
        String filePath = representativeImage.getFilePath();

        return ProductRecentViewed.of(productId, productName, productRepresentativeImage, filePath);
    }

}
