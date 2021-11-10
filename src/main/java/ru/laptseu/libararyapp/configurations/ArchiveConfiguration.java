package ru.laptseu.libararyapp.configurations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.Publisher;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "archiveEntityManager",
        transactionManagerRef = "archiveTransactionManager",
        basePackages = {"ru.laptseu.libararyapp.repositories.archive"}
)
public class ArchiveConfiguration {

    @Bean
    @ConfigurationProperties("spring.archive-datasource")
    public DataSourceProperties archiveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource archiveDataSource(@Qualifier("archiveDataSourceProperties") DataSourceProperties dataSourcePropertiesArchive) {
        return dataSourcePropertiesArchive.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean archiveEntityManager
            (@Qualifier("entityManagerFactoryBuilderArchive") EntityManagerFactoryBuilder builder,
             @Qualifier("archiveDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(BookArchived.class, Publisher.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager archiveTransactionManager(@Qualifier("archiveEntityManager") EntityManagerFactory productDSEmFactory) {
        return new JpaTransactionManager(productDSEmFactory);
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapterArchive(
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
    public EntityManagerFactoryBuilder entityManagerFactoryBuilderArchive(@Qualifier("hibernateJpaVendorAdapterArchive") HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        return new EntityManagerFactoryBuilder(hibernateJpaVendorAdapter,
                new HashMap(), null);
    }
}