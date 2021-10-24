package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.LoggingRepository;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.Calendar;

@Getter
@Service
public class BookLibraryService extends AbstractService<BookInLibrary> {
    private final BookArchivingMapper bookArchivingMapper;
    private final ServiceFactory serviceFactory;
    private final LoggingRepository loggingRepository;

    Class entityClass = BookInLibrary.class;

    public BookLibraryService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory, BookArchivingMapper bookArchivingMapper, ServiceFactory serviceFactory, LoggingRepository loggingRepository) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
        this.bookArchivingMapper = bookArchivingMapper;
        this.serviceFactory = serviceFactory;
        this.loggingRepository = loggingRepository;
    }


    @Override
    @Transactional(value = "libraryTransactionManager", rollbackFor = Exception.class)
    public BookArchived toArchive(BookInLibrary bookInLibraryForArchiving) {
        BookArchived bookArchived = bookArchivingMapper.map(bookInLibraryForArchiving);
        bookArchived.setId(null);
        bookArchived.setDateOfArchived(Calendar.getInstance());
        bookArchived = (BookArchived) serviceFactory.get(bookArchived.getClass()).save(bookArchived);
        serviceFactory.get(bookInLibraryForArchiving.getClass()).delete(bookInLibraryForArchiving.getId());
        loggingRepository.save("Book " + bookInLibraryForArchiving.getId() + " " + bookInLibraryForArchiving.getName() + " archived successfully");
        return bookArchived;
    }
}
