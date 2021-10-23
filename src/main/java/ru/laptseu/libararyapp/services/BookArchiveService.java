package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

import javax.naming.OperationNotSupportedException;

@Getter
@Service
public class BookArchiveService extends AbstractService<BookArchived> {
    private final BookArchivingMapper bookArchivingMapper;
    private final RepositoryFactory repositoryFactory;
    Class entityClass = BookArchived.class;

    public BookArchiveService(RepositoryFactory repositoryFactory, BookArchivingMapper bookArchivingMapper) {
        super(repositoryFactory);
        this.bookArchivingMapper = bookArchivingMapper;
        this.repositoryFactory = repositoryFactory;
    }

    @Transactional(value = "archiveTransactionManager", rollbackFor = Exception.class)
    public BookInLibrary fromArchive(BookArchived bookArchived) {
        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
        repositoryFactory.get(bookInLibrary.getClass()).save(bookInLibrary);
        repositoryFactory.get(bookArchived.getClass()).deleteById(bookArchived.getId());
        repositoryFactory.get(Entity.class).save("Book " + bookArchived.getId() + " " + bookArchived.getName() + " unarchived successfully");
        return bookInLibrary;
    }

    @Override
    public BookArchived toArchive(BookInLibrary bookInLibrary) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }


}
