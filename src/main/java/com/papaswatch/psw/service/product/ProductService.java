package com.papaswatch.psw.service.product;

import com.papaswatch.psw.common.utils.DateTimeUtils;
import com.papaswatch.psw.config.Constant;
import com.papaswatch.psw.dto.product.ProductInfo;
import com.papaswatch.psw.entity.UserInfoEntity;
import com.papaswatch.psw.entity.product.HashtagEntity;
import com.papaswatch.psw.entity.product.ProductEntity;
import com.papaswatch.psw.entity.product.ProductHashtagMappEntity;
import com.papaswatch.psw.entity.product.ProductImageEntity;
import com.papaswatch.psw.exceptions.ApplicationException;
import com.papaswatch.psw.repository.product.HashtagJpaRepository;
import com.papaswatch.psw.repository.product.ProductHashtagMappJpaRepository;
import com.papaswatch.psw.repository.product.ProductImageJpaRepository;
import com.papaswatch.psw.repository.product.ProductJpaRepository;
import com.papaswatch.psw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    @Value("${images.product.dir}")
    private String productDir;

    private final UserService userService;

    private final ProductJpaRepository productRepository;
    private final HashtagJpaRepository hashtagRepository;
    private final ProductHashtagMappJpaRepository productHashtagMappRepository;
    private final ProductImageJpaRepository productImageRepository;

    @Transactional(transactionManager = Constant.DB.TX)
    public boolean createProduct(String loginId, ProductInfo productInfo, List<MultipartFile> imageFiles) {
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
        List<ProductImageEntity> imageEntities = imageFiles.stream().map(it -> {
            String originalFilename = it.getOriginalFilename();

            if (originalFilename == null) throw ApplicationException.badRequest();

            int lastDot = originalFilename.lastIndexOf(".");
            String name = originalFilename.substring(0, lastDot);
            String extension = originalFilename.substring(lastDot + 1);

            String uuid = UUID.randomUUID().toString();
            String filePath = generatePath(uuid, extension);
            saveFile(it, filePath);

            return ProductImageEntity.createBy(name, uuid, filePath, extension);
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
        String directoryPath = productDir + File.separator + dir1 + File.separator + dir2;
        // 디렉토리 존재 확인 및 생성
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            throw new ApplicationException("Failed to create directories", e);
        }
        return directoryPath + File.separator + uuid + "." + extension;
    }

    /**
     * 파일을 지정된 경로에 저장하는 메서드
     */
    private void saveFile(MultipartFile file, String filePath) {
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new ApplicationException("Failed to save file", e);
        }
    }
}
