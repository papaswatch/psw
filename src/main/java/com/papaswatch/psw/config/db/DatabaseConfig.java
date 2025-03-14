package com.papaswatch.psw.config.db;

import com.papaswatch.psw.config.Constant;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@EnableJpaRepositories(
        basePackages = "com.papaswatch.psw.repository",
        entityManagerFactoryRef = Constant.DB.EM,
        transactionManagerRef = Constant.DB.TX
)
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean(Constant.DB.DS)
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(Constant.DB.EM)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier(Constant.DB.DS) DataSource dataSource,
            @Qualifier(Constant.DB.JPA_PROP) Properties properties
    ) {
        log.info("Database Configured......");
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(
                "com.papaswatch.psw.entity"
        );
        entityManagerFactoryBean.setJpaProperties(properties);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return entityManagerFactoryBean;
    }

    @Bean(Constant.DB.TX)
    public PlatformTransactionManager transactionManager(
            @Qualifier(Constant.DB.EM) EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
