package ru.laptseu.libararyapp.Config;


import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:ru/laptseu/libararyapp/application.yml"})
@EnableJpaRepositories(
//        basePackages = "com.baeldung.multipledb.dao.user",
//        entityManagerFactoryRef = "userEntityManager",
//        transactionManagerRef = "userTransactionManager"
)
public class Library {
    @Autowired
    private Environment env;

    @Primary
    @Bean
    @ConfigurationProperties(prefix="spring.datasource-library")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean userEntityManager() {
//        LocalContainerEntityManagerFactoryBean em
//                = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(userDataSource());
//        em.setPackagesToScan(
//                new String[] { "com.baeldung.multipledb.model.user" });
//
//        HibernateJpaVendorAdapter vendorAdapter
//                = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto",
//                env.getProperty("hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.dialect",
//                env.getProperty("hibernate.dialect"));
//        em.setJpaPropertyMap(properties);
//
//        return em;
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager userTransactionManager() {
//
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                userEntityManager().getObject());
//        return transactionManager;
//    }
}


