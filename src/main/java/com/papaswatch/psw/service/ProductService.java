package com.papaswatch.psw.service;

import com.papaswatch.psw.config.Constant;
import com.papaswatch.psw.dto.product.*;
import com.papaswatch.psw.entity.CartEntity;
import com.papaswatch.psw.entity.ProductLikedEntity;
import com.papaswatch.psw.entity.UserInfoEntity;
import com.papaswatch.psw.entity.product.*;
import com.papaswatch.psw.exceptions.ApplicationException;
import com.papaswatch.psw.repository.CartRepository;
import com.papaswatch.psw.repository.ProductLikedRepository;
import com.papaswatch.psw.repository.product.*;
import com.papaswatch.psw.repository.product.recentView.CachedRecentViewedRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    // 최근 본 상품의 저장될 최대 갯수 지정
    private final int MAX_RECENT_VIEWED_QUANTITY = 5;

    @Value("${images.product.dir}")
    private String productImgDir;

    private final UserService userService;
    private final CartRepository cartRepository;
    private final ProductLikedRepository productLikedRepository;
    private final ProductJpaRepository productRepository;
    private final CachedRecentViewedRepository recentViewedRepository;
    private final HashtagJpaRepository hashtagRepository;
    private final ProductHashtagMappJpaRepository productHashtagMappRepository;
    private final ProductImageJpaRepository productImageRepository;
    private final ProductQuery productQuery;
    private final ReviewJpaRepository reviewRepository;


    /**
     * 상품을 등록하는 메서드입니다.
     */
    @Transactional(transactionManager = Constant.DB.TX)
    public boolean addProduct(String loginId, CreateProductRequest productInfo, List<MultipartFile> imageFiles) {
        log.debug("productInfo: {}", productInfo);
        /* 사용자 검증. */
        UserInfoEntity userInfo = userService.getUserInfo(loginId);

        /* 상품 엔티티 */
        ProductEntity productEntity = ProductEntity.of(
                productInfo.getName(),
                productInfo.getDescription(),
                productInfo.getBrand(),
                productInfo.getStock(),
                productInfo.getPrice(),
                userInfo);

        /* product 이미지 처리 */
        List<ProductImageEntity> imageEntities = IntStream.range(0, imageFiles.size())
                .mapToObj(i -> {
                    MultipartFile it = imageFiles.get(i);
                    String originalFilename = it.getOriginalFilename();

                    if (originalFilename == null) throw ApplicationException.badRequest();

                    int lastDot = originalFilename.lastIndexOf(".");
                    String name = originalFilename.substring(0, lastDot);
                    String extension = originalFilename.substring(lastDot + 1);

                    String uuid = UUID.randomUUID().toString();
                    String filePath = generatePath(uuid, extension);
                    saveFile(it, filePath);

                    boolean isThumbnail = (i == 0); // 첫 번째 이미지만 썸네일 설정

                    return ProductImageEntity.createBy(name, uuid, filePath, extension, isThumbnail);
                }).toList();

        productEntity.addImages(imageEntities);

        /* 모두 저장 */
        productRepository.save(productEntity);
        productImageRepository.saveAll(imageEntities);
        /* product tag 처리 */
        List<String> hashtags = productInfo.getHashtags();
        if (hashtags != null && !hashtags.isEmpty()) {
            List<HashtagEntity> hashtagEntities = hashtags.stream().map(this::findOrCreateHashtag).toList();
            List<ProductHashtagMappEntity> productHashtagMappEntities = hashtagEntities.stream().map(it -> ProductHashtagMappEntity.of(it, productEntity)).toList();
            productEntity.addHashtagMapps(productHashtagMappEntities);
            productHashtagMappRepository.saveAll(productHashtagMappEntities);
        }

        return true;
    }

    /**
     * 상품 리스트를 읽는 메서드입니다.
     */
    @Transactional(transactionManager = Constant.DB.TX, readOnly = true)
    public List<Product> getProducts(SearchProductRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getRows());
        List<Product> products = productQuery.findProductsBy(request, pageable);
        List<Long> productIds = products.stream().map(Product::getProductId).toList();
        List<ProductHashtag> productHashtags = productQuery.findProductHashtagsBy(productIds);
        //Map<Long, List<ProductHashtag>> map = productHashtags.stream().collect(Collectors.groupingBy(ProductHashtag::getProductId));
        Map<Long, List<String>> map = productHashtags.stream().collect(Collectors.groupingBy(ProductHashtag::getProductId, Collectors.mapping(ProductHashtag::getHashtag, Collectors.toList())));
        products.forEach(it -> it.addHashtags(map.get(it.getProductId())));
        return products;
    }


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
        CartEntity cartEntity = cartRepository.findById(cartId).orElseThrow(ApplicationException::cartDataNotFound);
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
        ProductLikedEntity productLikedEntity = productLikedRepository.findById(productId).orElseThrow(ApplicationException::productLikedNotFound);

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
    public List<Long> addRecentViewedProduct(long productId, HttpSession session) {
        long loginUserId = userService.getUserId(session);

        // 현재 접속중인 유저의 id의 Queue 정보를 가져옵니다. 없으면 새로운 Queue를 넣어줍니다.
        Queue<Long> userQueue = recentViewedRepository.getRecentViewedProductQueue(loginUserId);
        if (userQueue == null) {
            userQueue = new ArrayDeque<>();
        }
        log.debug("check user :: {} recent viewed :: {}", loginUserId, userQueue.toString());

        //최근 본 상품의 크기가 5 이상이면 우선 한개 빼고 시작
        if (userQueue.size() >= MAX_RECENT_VIEWED_QUANTITY) {
            userQueue.poll();
        }

        // 추가 될 최근 본 상품에 대한 정보를 생성합니다.
        userQueue.offer(productId);
        recentViewedRepository.saveRecentViewedProduct(loginUserId, userQueue);

        return createRecentViewedProductResponse(userQueue);
    }

    /**
     * 유저의 최근 본 상품 Response 데이터를 생성합니다.
     * @param recentViewedQueue
     * @return
     */
    public List<Long> createRecentViewedProductResponse(Queue<Long> recentViewedQueue) {
        List<Long> response = new ArrayList<>(recentViewedQueue);
        Collections.reverse(response);
        log.debug("check recent viewed response :: {}", response.toString());
        return response;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 태그를 찾거나 생성하는 메서드
     */
    private HashtagEntity findOrCreateHashtag(String tagName) {
        return hashtagRepository.findByName(tagName)
                .orElseGet(() -> hashtagRepository.save(HashtagEntity.of(tagName)));
    }

    /**
     * 파일 경로를 생성하고, 디렉토리가 없으면 생성하는 메서드
     */
    private String generatePath(String uuid, String extension) {
        String dir1 = uuid.substring(0, 2);
        String dir2 = uuid.substring(2, 4);

//        String datetime = DateTimeUtils.currentYearToDateSlash();
//        String directoryPath = String.format("%s\\%s\\%s\\%s", productDir, dir1, dir2, datetime);
        String hashDir = File.separator + dir1 + File.separator + dir2;
        String directoryPath = productImgDir + hashDir;
        // 디렉토리 존재 확인 및 생성
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            throw new ApplicationException("Failed to create directories", e);
        }
        return hashDir + File.separator + uuid + "." + extension;
    }

    /**
     * 파일을 지정된 경로에 저장하는 메서드
     */
    private void saveFile(MultipartFile file, String filePath) {
        try {
            file.transferTo(new File(productImgDir + File.separator + filePath));
        } catch (IOException e) {
            throw new ApplicationException("Failed to save file", e);
        }
    }

    /**
     * 제품 리뷰를 등록합니다.
     * @param productId
     * @param productReview
     * @param stars
     * @param session
     */
    @Transactional
    public ReviewResponse addProductReview(long productId, String productReview, int stars, HttpSession session) {
        String userLoginId = userService.getUserLoginId(session);
        UserInfoEntity userInfo = userService.getUserInfo(userLoginId);
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(ApplicationException::productNotFound);

        ReviewEntity reviewEntity = ReviewEntity.of(productReview, productEntity, userInfo, stars);
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);

        return ReviewResponse.fromEntity(savedReview);
    }

    /**
     * 제품 리뷰를 삭제합니다.
     * @param reviewId
     * @param session
     * @return
     */
    @Transactional
    public boolean deleteProductReview(long reviewId, HttpSession session) {
        long userId = userService.getUserId(session);
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId).orElseThrow(ApplicationException::reviewNotFount);
        if (!reviewEntity.getUser().getUserId().equals(userId)) {
            return false;
        }
        reviewRepository.delete(reviewEntity);
        return true;
    }
}
