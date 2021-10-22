package ru.laptseu.libararyapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ServiceFactory {
    private static final Map<Class<? extends Entity>, Class<? extends AbstractService>> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put(BookInLibrary.class, BookLibraryService.class);
        FACTORY_MAP.put(BookArchived.class, BookArchiveService.class);
        FACTORY_MAP.put(Publisher.class, PublisherService.class);
        FACTORY_MAP.put(Author.class, AuthorService.class);
    }

    private final ApplicationContext applicationContext;

    public AbstractService get(Class clazz) {
        return applicationContext.getBean(FACTORY_MAP.get(clazz));
    }
}
