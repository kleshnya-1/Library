package ru.laptseu.libararyapp.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.books.ArchivedBook;
import ru.laptseu.libararyapp.entities.books.Book;
import ru.laptseu.libararyapp.entities.books.LibraryBook;
import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.repositories.archive.BookArchiveRepository;
import ru.laptseu.libararyapp.repositories.library.AuthorRepository;
import ru.laptseu.libararyapp.repositories.library.BookLibraryRepository;
import ru.laptseu.libararyapp.repositories.library.PublisherRepository;

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
        FACTORY_MAP.put(Book.class, BookLibraryRepository.class);
        FACTORY_MAP.put(Publisher.class, PublisherRepository.class);
    }

    private final ApplicationContext applicationContext;

    public PagingAndSortingRepository get(Class clazz) {
        return applicationContext.getBean(FACTORY_MAP.get(clazz));
    }
}
