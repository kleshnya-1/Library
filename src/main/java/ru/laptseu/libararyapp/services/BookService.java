package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.books.Book;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

@Getter
@Setter
@Service
public class BookService extends AbstractService<Book> {
    Class entityClass= Book.class;

    public BookService(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
    }
}
