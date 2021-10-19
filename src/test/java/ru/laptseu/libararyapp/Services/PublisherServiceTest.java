package ru.laptseu.libararyapp.Services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import ru.laptseu.libararyapp.Entities.Publisher;
import ru.laptseu.libararyapp.Repositories.RepositoryFactory;

class PublisherServiceTest {
    @Test
    void testConstructor() {
        RepositoryFactory repositoryFactory = new RepositoryFactory(new AnnotationConfigReactiveWebApplicationContext());
        PublisherService actualPublisherService = new PublisherService(null, repositoryFactory);

        assertNull(actualPublisherService.getBookArchivingMapper());
        RepositoryFactory repositoryFactory1 = actualPublisherService.getRepositoryFactory();
        assertSame(repositoryFactory, repositoryFactory1);
        Class expectedEntityClass = actualPublisherService.entityClass;
        Class entityClass = actualPublisherService.getEntityClass();
        assertSame(expectedEntityClass, entityClass);
        assertSame(Publisher.class, entityClass);
        assertNull(null);
        assertSame(repositoryFactory1, repositoryFactory);
    }
}

