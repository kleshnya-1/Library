package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.models.entities.LoggingEntity;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.Calendar;
import java.util.stream.Collectors;

@Log4j2
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
    @Transactional(value = "libraryTransactionManager", rollbackFor = Exception.class)
    public BookArchived toArchive(BookInLibrary bookInLibraryForArchiving) {
        BookArchived bookArchived = bookArchivingMapper.map(bookInLibraryForArchiving);
        bookArchived.setId(null);
        bookArchived.setDateOfArchived(Calendar.getInstance());
        bookArchived = (BookArchived) serviceFactory.get(BookArchived.class).save(bookArchived);
        serviceFactory.get(BookInLibrary.class).delete(bookInLibraryForArchiving.getId());
        try {
            serviceFactory.get(LoggingEntity.class).save(new LoggingEntity("Book " +
                    bookInLibraryForArchiving.getId() + " " + bookInLibraryForArchiving.getName() + " archived successfully"));
        } catch (Exception e) {
            log.error(e);
        }
        return bookArchived;
    }

    @Override
    public BookInLibrary read(Long id) {
        BookInLibrary a = super.read(id);
        a.setAuthorList(a.getAuthorList().stream().filter(b -> b.isDeleted() == false).collect(Collectors.toList()));
        if (a.getPublisher() != null && a.getPublisher().isDeleted() == true) {
            a.setPublisher(null);
        }
        return a;
    }
}
