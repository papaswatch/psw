package com.papaswatch.psw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PswApplication {

    public static void main(String[] args) {
        SpringApplication.run(PswApplication.class, args);
    }

}
