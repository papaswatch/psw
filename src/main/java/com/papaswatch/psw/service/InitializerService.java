package com.papaswatch.psw.service;

import com.papaswatch.psw.common.utils.*;
import com.papaswatch.psw.config.Constant;
import com.papaswatch.psw.entity.UserInfoEntity;
import com.papaswatch.psw.entity.product.ProductEntity;
import com.papaswatch.psw.entity.product.ProductImageEntity;
import com.papaswatch.psw.exceptions.ApplicationException;
import com.papaswatch.psw.repository.product.ProductImageJpaRepository;
import com.papaswatch.psw.repository.product.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class InitializerService {

    @Value("${images.sample.path}")
    private String imagesSamplePath;
    @Value("${images.product.dir}")
    private String productImgDir;


    private final String DOT = ".";
    private final String SLASH = "/";

    private final Integer MAX_PRICE = 10_000_000;
    private final Integer MIN_PRICE = 1_000_000;

    private final Integer MAX_STOCK = 1000;
    private final Integer MIN_STOCK = 100;

    private static final Random random = new Random();

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final KoreanNameGenerator koreanNameGenerator;

    private final WatchNameGenerator watchNameGenerator;

    private final HashtagGenerator hashtagGenerator;

    private final ProductJpaRepository productRepository;
    private final ProductImageJpaRepository productImageRepository;

    @Transactional(transactionManager = Constant.DB.TX)
    public void initializeSampleUser(Integer initUserSize) {
        log.debug("Start to initialize sample user");
        userService.deleteAll();

        /* 반환받을 리스트 */
        List<UserInfoEntity> list = new ArrayList<>();
        /* 임의의 아이디 생성기, 이걸 여기서 생성한 이유가 있는데, 생성 이후에 가비지 컬렉터가 메모리를 비우도록 하려고 메서드 레벨에서 생성 */
        UserIdGenerator userIdGenerator = new UserIdGenerator();

        Set<String> randomUserIds = userIdGenerator.generateRandomUserIds(initUserSize);
        for (String randomUserId : randomUserIds) {
            int i = random.nextInt(2);
            String name = koreanNameGenerator.generate(i);// i 가 0 일 경우 남자, 1일 경우 여자임. 사용자 도메인 성별을 구분하는 필드는 없는데, 추후에 고려해보던가

            UserInfoEntity userInfoEntity =
                    UserInfoEntity.createBy(randomUserId, passwordEncoder.encode("1234"), name, EmailGenerator.generateEmail(randomUserId), PhoneNumberGenerator.generatePhoneNumber());
            list.add(userInfoEntity);
        }
        /* 모두 저장 처리 */
        userService.createAll(list);
        log.debug("end initializing sample user");
    }

    /* 판매자가 상품을 등록하는 것처럼 구현. */
    @Transactional(transactionManager = Constant.DB.TX)
    public void initializeSampleProduct() {
        List<ProductEntity> productEntities = new ArrayList<>();
        List<ProductImageEntity> productImageEntities = new ArrayList<>();

        /* 랜덤한 유저가 판매 상품을 등록했다고 하기 위해 일단 사용자를 전부 조회해 온다. */
        List<UserInfoEntity> allUsers = userService.getAll();
        int userSize = allUsers.size();


        Map<String, List<String>> productImageMap = extractProductImage();
        productImageMap.forEach((key, value) -> {
            String brand = key;
            List<String> images = value;

            images.stream().forEach(image -> {
                int index = random.nextInt(userSize);
                UserInfoEntity randomUser = allUsers.get(index);

                int price = (int)(Math.random() * (MAX_PRICE - MIN_PRICE + 1)) + MIN_PRICE;
                int stock = (int)(Math.random() * (MAX_STOCK - MIN_STOCK + 1)) + MIN_STOCK;
                String productName = String.format("%s %s", brand, watchNameGenerator.generateName());
                ProductEntity productEntity = ProductEntity.of(productName, productName, brand, stock, price, randomUser);

                ///////////////////////

                int lastDot = image.lastIndexOf(DOT);
                String name = image.substring(0, lastDot);
                String extension = image.substring(lastDot + 1);

                String uuid = UUID.randomUUID().toString();
                String filePath = generatePath(uuid);

                ProductImageEntity productImageEntity = ProductImageEntity.createBy(name, uuid, filePath, extension, true);
                productEntity.addImages(List.of(productImageEntity));

                productEntities.add(productEntity);
                productImageEntities.add(productImageEntity);
                saveFile(image, filePath, uuid, extension);
            });
        });

        productRepository.saveAll(productEntities);
        productImageRepository.saveAll(productImageEntities);
    }

    private Map<String, List<String>> extractProductImage() {
        FileSystemResource resource = new FileSystemResource(imagesSamplePath);
        Map<String, List<String>> map = new HashMap<>();
        if (resource.exists()) {
            File dir = resource.getFile(); // 클래스패스가 파일 시스템에 있어야 가능 (IDE나 JAR 외부 실행 시 가능)
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String filename = file.getName();
                        Pattern pattern = Pattern.compile("^([^0-9]*)[0-9]");
                        Matcher matcher = pattern.matcher(filename);

                        if (!matcher.find()) {
                            continue;
                        }
                        String brand = matcher.group(1);
                        map.computeIfAbsent(brand, x -> new ArrayList<>()).add(filename);
                    }
                }
            } else {
                log.warn("지정한 경로는 디렉토리가 아닙니다.");
            }
        } else {
            log.error("경로가 존재하지 않습니다.");
        }
        return map;
    }

    private String generatePath(String uuid) {
        String dir1 = uuid.substring(0, 2);
        String dir2 = uuid.substring(2, 4);

//        String datetime = DateTimeUtils.currentYearToDateSlash();
//        String directoryPath = String.format("%s\\%s\\%s\\%s", productDir, dir1, dir2, datetime);
        String hashDir = SLASH + dir1 + SLASH + dir2;
        String directoryPath = productImgDir + hashDir;
        // 디렉토리 존재 확인 및 생성
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            throw new ApplicationException("Failed to create directories", e);
        }
        return hashDir;
    }

    private void saveFile(String image, String filePath, String uuid, String extension) {

        Path source = Paths.get(imagesSamplePath + SLASH + image);
        Path target = Paths.get(productImgDir + SLASH + filePath + SLASH + uuid + DOT + extension);

        // 디렉토리가 없으면 생성
        if (Files.notExists(target.getParent())) {
            try {
                Files.createDirectories(target.getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (Files.exists(source)) {
            try {
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
