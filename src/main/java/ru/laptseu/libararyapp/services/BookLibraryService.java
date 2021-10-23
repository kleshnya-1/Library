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
import java.util.Calendar;

@Getter
@Service
public class BookLibraryService extends AbstractService<BookInLibrary> {
    private final BookArchivingMapper bookArchivingMapper;
    private final RepositoryFactory repositoryFactory;
    Class entityClass = BookInLibrary.class;

    public BookLibraryService(RepositoryFactory repositoryFactory, BookArchivingMapper bookArchivingMapper) {
        super(repositoryFactory);
        this.bookArchivingMapper = bookArchivingMapper;
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    @Transactional(value = "libraryTransactionManager", rollbackFor = Exception.class)

    public BookArchived toArchive(BookInLibrary bookInLibrary) {
        BookArchived bookArchived = bookArchivingMapper.map(bookInLibrary);
        bookArchived.setId(null);
        bookArchived.setDateOfArchived(Calendar.getInstance());
        repositoryFactory.get(bookArchived.getClass()).save(bookArchived);
        repositoryFactory.get(bookInLibrary.getClass()).deleteById(bookInLibrary.getId());
        repositoryFactory.get(Entity.class).save("Book " + bookInLibrary.getId() + " " + bookInLibrary.getName() + " archived successfully");
        return bookArchived;
    }

    @Override
    public BookInLibrary fromArchive(BookArchived bookArchived) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
