package com.papaswatch.psw.config.db;

import com.papaswatch.psw.config.Constant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class JpaPropertiesConfig {

    @ConfigurationProperties(prefix = "spring.jpa.properties")
    @Bean(Constant.DB.JPA_PROP)
    public Properties jpaProperties() {
        return new Properties();
    }
}
