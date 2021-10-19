package ru.laptseu.libararyapp.Services;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.Books.Book;
import ru.laptseu.libararyapp.Repositories.RepositoryFactory;

@Getter
@Setter
public class BookService extends AbstractService<Book> {
    Class entityClass;
    public BookService(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
        this.entityClass = Book.class;
    }
}
