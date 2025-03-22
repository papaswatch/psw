package com.papaswatch.psw.service;

import com.papaswatch.psw.entity.Cart;
import com.papaswatch.psw.entity.ProductLiked;
import com.papaswatch.psw.exceptions.ApplicationException;
import com.papaswatch.psw.repository.CartRepository;
import com.papaswatch.psw.repository.ProductLikedRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final UserService userService;
    private final CartRepository cartRepository;
    private final ProductLikedRepository productLikedRepository;

    /**
     * 장바구니에 추가
     * @param productId
     * @param quantity
     * @param session
     * @return
     */
    @Transactional
    public boolean addCart(long productId, int quantity, HttpSession session) {
        long userId = userService.getUserId(session);
        try {
            cartRepository.save(Cart.create(userId, productId, quantity));
            return true;
        } catch (Exception e) {
            log.error("failed to add cart to user {}", userId);
            return false;
        }
    }

    /**
     * 장바구니에서 제거
     * @param cartId
     * @param session
     * @return
     */
    @Transactional
    public boolean removeCart(long cartId, HttpSession session) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ApplicationException::CartDataNotFound);
        long userIdInCart = cart.getUserId();
        long currentUserId = userService.getUserId(session);
        // 현재 접속한 유저의 아이디와 장바구니 정보에 저장된 유저 아이디가 같으면 해당 장바구니데이터를 삭제합니다
        if (userIdInCart == currentUserId) {
            try {
                cartRepository.delete(cart);
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
     * @param productId
     * @param session
     * @return
     */
    @Transactional
    public boolean addProductLiked(long productId, HttpSession session) {
        long userId = userService.getUserId(session);
        try {
            productLikedRepository.save(ProductLiked.create(userId, productId));
            return true;
        } catch (Exception e) {
            log.error("failed to add product liked to user {}", userId);
            return false;
        }
    }

    /**
     * 찜목록에서 삭제
     * @param productId
     * @param session
     * @return
     */
    @Transactional
    public boolean deleteProductLiked(long productId, HttpSession session) {
        long currentUserId = userService.getUserId(session);
        ProductLiked productLiked = productLikedRepository.findById(productId).orElseThrow(ApplicationException::ProductLikedNotFound);

        if (productLiked.getUserId() == currentUserId) {
            try {
                productLikedRepository.delete(productLiked);
                return true;
            } catch (Exception e) {
                log.error("failed to remove product liked to user {}", currentUserId);
                return false;
            }
        }
        return false;
    }
}
