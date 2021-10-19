package ru.laptseu.libararyapp.Services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.Entities.Books.Book;
import ru.laptseu.libararyapp.Mappers.BookArchivingMapper;
import ru.laptseu.libararyapp.Repositories.RepositoryFactory;

@Getter
@Setter
@Service

public class BookService extends AbstractService<Book> {
    Class entityClass;

    public BookService(BookArchivingMapper bookArchivingMapper, RepositoryFactory repositoryFactory) {
        super(bookArchivingMapper, repositoryFactory);
        this.entityClass = Book.class;
    }
}
