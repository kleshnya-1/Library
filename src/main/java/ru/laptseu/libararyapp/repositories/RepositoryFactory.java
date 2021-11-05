package ru.laptseu.libararyapp.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.EntityWithId;
import ru.laptseu.libararyapp.models.entities.LoggingEntity;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.repositories.archive.BookArchiveRepository;
import ru.laptseu.libararyapp.repositories.library.AuthorRepository;
import ru.laptseu.libararyapp.repositories.library.BookLibraryRepository;
import ru.laptseu.libararyapp.repositories.library.PublisherRepository;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RepositoryFactory {
    private static final Map<Class<? extends EntityWithId>, Class<? extends AbstractRepository>> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put(Author.class, AuthorRepository.class);
        FACTORY_MAP.put(BookArchived.class, BookArchiveRepository.class);
        FACTORY_MAP.put(BookInLibrary.class, BookLibraryRepository.class);
        FACTORY_MAP.put(Publisher.class, PublisherRepository.class);
        FACTORY_MAP.put(LoggingEntity.class, LoggingRepository.class);
    }

    private final ApplicationContext applicationContext;

    public AbstractRepository get(Class clazz) {
        return applicationContext.getBean(FACTORY_MAP.get(clazz));
    }
}
