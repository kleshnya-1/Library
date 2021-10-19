package ru.laptseu.libararyapp.Repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.Entities.Author;
import ru.laptseu.libararyapp.Entities.Books.ArchivedBook;
import ru.laptseu.libararyapp.Entities.Books.LibraryBook;
import ru.laptseu.libararyapp.Entities.Entity;
import ru.laptseu.libararyapp.Entities.Publisher;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RepositoryFactory {
    private static final Map<Class<? extends Entity>, Class<? extends PagingAndSortingRepository>> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put(Author.class, AuthorRepository.class);
        FACTORY_MAP.put(ArchivedBook.class, BookArchiveRepository.class);
        FACTORY_MAP.put(LibraryBook.class, BookLibraryRepository.class);
        FACTORY_MAP.put(Publisher.class, PublisherRepository.class);
    }

    private final ApplicationContext applicationContext;

    public PagingAndSortingRepository get(Class clazz) {
        return applicationContext.getBean(FACTORY_MAP.get(clazz));
    }
}
