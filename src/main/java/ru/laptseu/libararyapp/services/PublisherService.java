package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

import javax.naming.OperationNotSupportedException;

@Getter
@Setter
@Service

public class PublisherService extends AbstractService<Publisher> {
    Class entityClass = Publisher.class;


    public PublisherService(RepositoryFactory repositoryFactory) {
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
