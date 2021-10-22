package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

@Getter
@Service
public class BookLibraryService extends AbstractService<BookInLibrary> {
    Class entityClass = BookInLibrary.class;

    public BookLibraryService(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
    }
}
