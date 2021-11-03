package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.entities.LoggingEntity;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.Calendar;
import java.util.List;

@Getter
@Service
public class BookLibraryService extends AbstractService<BookInLibrary> {
    private final BookArchivingMapper bookArchivingMapper;
    private final ServiceFactory serviceFactory;
    private final RepositoryFactory repositoryFactory;

    public BookLibraryService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory,
                              BookArchivingMapper bookArchivingMapper, ServiceFactory serviceFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
        this.bookArchivingMapper = bookArchivingMapper;
        this.serviceFactory = serviceFactory;
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public BookArchived toArchive(BookInLibrary bookInLibraryForArchiving) {
        BookArchived bookArchived = toArchiveTransaction(bookInLibraryForArchiving);
        serviceFactory.get(LoggingEntity.class).save(new LoggingEntity("Book " + bookInLibraryForArchiving.getId() + " " + bookInLibraryForArchiving.getName() + " archived successfully"));
        return bookArchived;
    }


    @Transactional(value = "libraryTransactionManager", rollbackFor = Exception.class)
    public BookArchived toArchiveTransaction(BookInLibrary bookInLibraryForArchiving) {
        BookArchived bookArchived = bookArchivingMapper.map(bookInLibraryForArchiving);
        bookArchived.setId(null);
        bookArchived.setDateOfArchived(Calendar.getInstance());
        bookArchived = (BookArchived) serviceFactory.get(BookArchived.class).save(bookArchived);
        serviceFactory.get(BookInLibrary.class).delete(bookInLibraryForArchiving.getId());
        return bookArchived;
    }

//    @Override
//    public List<BookInLibrary> readBooksByAuthor(Long id) {
//        return repositoryFactory.get(getEntityClass()).findByAuthorId(id);
//    }
//
//    @Override
//    public List<BookInLibrary> readBooksByPublisher(Long id) {
//        return repositoryFactory.get(getEntityClass()).findByPublisherId(id);
//    }
}
