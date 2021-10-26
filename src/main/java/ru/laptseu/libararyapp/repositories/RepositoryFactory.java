package ru.laptseu.libararyapp.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.archive.BookArchiveRepository;
import ru.laptseu.libararyapp.repositories.library.AuthorRepository;
import ru.laptseu.libararyapp.repositories.library.BookLibraryRepository;
import ru.laptseu.libararyapp.repositories.library.PublisherRepository;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RepositoryFactory {
    private static final Map<Class<? extends Entity>, Class<? extends AbstractRepository>> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put(Author.class, AuthorRepository.class);
        FACTORY_MAP.put(BookArchived.class, BookArchiveRepository.class);
        FACTORY_MAP.put(BookInLibrary.class, BookLibraryRepository.class);
        FACTORY_MAP.put(Publisher.class, PublisherRepository.class);
        FACTORY_MAP.put(Entity.class, LoggingRepository.class); // Entity as superClass don't have own persisting repository and here uses for mapping this logging repository (.txt)
    }

    private final ApplicationContext applicationContext;

    public AbstractRepository get(Class clazz) {
        return applicationContext.getBean(FACTORY_MAP.get(clazz));
    }
}
