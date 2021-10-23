package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

import javax.naming.OperationNotSupportedException;

@Getter
@Service
public class AuthorService extends AbstractService<Author> {
    Class entityClass = Author.class;


    public AuthorService(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
    }

    @Override
    public BookArchived toArchive(BookInLibrary bookInLibrary) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Override
    public BookInLibrary fromArchive(BookArchived bookArchived) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
