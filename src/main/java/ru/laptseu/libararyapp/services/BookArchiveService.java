package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

@Getter
@Service
public class BookArchiveService extends AbstractService<BookArchived> {
    Class entityClass = BookArchived.class;

    public BookArchiveService(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
    }
}
