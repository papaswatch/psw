package com.papaswatch.psw.service;

import com.papaswatch.psw.common.utils.EmailGenerator;
import com.papaswatch.psw.common.utils.KoreanNameGenerator;
import com.papaswatch.psw.common.utils.PhoneNumberGenerator;
import com.papaswatch.psw.common.utils.UserIdGenerator;
import com.papaswatch.psw.config.Constant;
import com.papaswatch.psw.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class InitializerService {

    @Value("${images.sample.path}")
    private String imagesSamplePath;

    private static final Random random = new Random();

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final KoreanNameGenerator koreanNameGenerator;


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

    public void initializeSampleProduct() {
        FileSystemResource resource = new FileSystemResource(imagesSamplePath);

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

                    }
                }
            } else {
                System.out.println("지정한 경로는 디렉토리가 아닙니다.");
            }
        } else {
            System.out.println("경로가 존재하지 않습니다.");
        }


    }
}
