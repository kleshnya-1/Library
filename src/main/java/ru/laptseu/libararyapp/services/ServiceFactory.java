package ru.laptseu.libararyapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.LibraryBook;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ServiceFactory {
    private static final Map<Class<? extends Entity>, Class<? extends AbstractService>> FACTORY_MAP = new HashMap<>();

    static {
//        FACTORY_MAP.put(Author.class, AuthorRepository.class);
//        FACTORY_MAP.put(ArchivedBook.class, BookArchiveRepository.class);
        FACTORY_MAP.put(LibraryBook.class, BookService.class);
        FACTORY_MAP.put(Publisher.class, PublisherService.class);
    }

    private final ApplicationContext applicationContext;

    public AbstractService get(Class clazz) {
        return applicationContext.getBean(FACTORY_MAP.get(clazz));
    }
}
