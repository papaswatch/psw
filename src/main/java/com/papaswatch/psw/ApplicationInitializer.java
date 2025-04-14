package com.papaswatch.psw;

import com.papaswatch.psw.service.InitializerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements ApplicationRunner {

    @Value("${init.user.size}")
    private Integer initUserSize;

    private final InitializerService initializerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /* 이 메서드를 실행하면 애플리케이션이 재구동 될 때마다 모두 지우고, 임의의 유저를 만들어 낸다. */
        initializerService.initializeSampleUser(initUserSize);
        /* 상품 자동등록, */
        initializerService.initializeSampleProduct();
        log.info("Application initialized..");
    }
}
