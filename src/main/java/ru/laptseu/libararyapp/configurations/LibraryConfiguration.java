package ru.laptseu.libararyapp.configurations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


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

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");//TODO: 21.10.2021 refactor to properties
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean libraryEntityManager
            (EntityManagerFactoryBuilder builder, @Qualifier("libraryDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean l = builder
                .dataSource(dataSource)
                .packages(BookInLibrary.class, Publisher.class, Author.class)
                .build();
        l.setJpaProperties(additionalProperties());
        return l;
    }

    @Bean
    public PlatformTransactionManager libraryTransactionManager(@Qualifier("libraryEntityManager") EntityManagerFactory productDSEmFactory) {
        return new JpaTransactionManager(productDSEmFactory);
    }
}
