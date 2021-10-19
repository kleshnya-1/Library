package ru.laptseu.libararyapp.Services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.Entities.Books.Book;
import ru.laptseu.libararyapp.Entities.Publisher;
import ru.laptseu.libararyapp.Mappers.BookArchivingMapper;
import ru.laptseu.libararyapp.Repositories.RepositoryFactory;

@Getter
@Setter
@Service

public class PublisherService extends AbstractService<Publisher> {
    Class entityClass = Publisher.class;

    public PublisherService(BookArchivingMapper bookArchivingMapper, RepositoryFactory repositoryFactory) {
        super(bookArchivingMapper, repositoryFactory);
    }
}
