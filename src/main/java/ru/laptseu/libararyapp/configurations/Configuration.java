package ru.laptseu.libararyapp.configurations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Properties;

@org.springframework.context.annotation.Configuration

public class Configuration {

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(
            @Value("${spring.jooq.sql-dialect}") String databasePlatform,
            @Value("${spring.jpa.generate-ddl}") boolean generateDdl,
            @Value("${spring.jpa.show-sql}") boolean showSql) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform(databasePlatform);
        jpaVendorAdapter.setGenerateDdl(generateDdl);
        jpaVendorAdapter.setShowSql(showSql);
        return jpaVendorAdapter;
    }


    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(@Qualifier("hibernateJpaVendorAdapter") HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        return new EntityManagerFactoryBuilder(hibernateJpaVendorAdapter,
                new HashMap(), null);
    }

    @Bean
    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");//TODO: 21.10.2021 refactor to properties
        return properties;
    }
}