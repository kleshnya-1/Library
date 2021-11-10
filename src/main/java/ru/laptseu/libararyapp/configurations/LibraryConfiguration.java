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
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.models.entities.Publisher;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "libraryEntityManager",
        transactionManagerRef = "libraryTransactionManager",
        basePackages = {"ru.laptseu.libararyapp.repositories.library"}
)
public class LibraryConfiguration {
    @Bean
    @ConfigurationProperties("spring.library-datasource")
    public DataSourceProperties libraryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource libraryDataSource(@Qualifier("libraryDataSourceProperties") DataSourceProperties dataSourcePropertiesLibrary) {
        return dataSourcePropertiesLibrary.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean libraryEntityManager
            (@Qualifier("entityManagerFactoryBuilderLibrary") EntityManagerFactoryBuilder builder,
             @Qualifier("libraryDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(BookInLibrary.class, Publisher.class, Author.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager libraryTransactionManager(@Qualifier("libraryEntityManager") EntityManagerFactory productDSEmFactory) {
        return new JpaTransactionManager(productDSEmFactory);
    }


    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapterLibrary(
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
    public EntityManagerFactoryBuilder entityManagerFactoryBuilderLibrary(@Qualifier("hibernateJpaVendorAdapterLibrary") HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        return new EntityManagerFactoryBuilder(hibernateJpaVendorAdapter,
                new HashMap(), null);
    }
}
