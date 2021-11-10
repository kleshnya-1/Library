package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.models.entities.Logging;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.HibernateUnproxyUtility;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Getter
@Service
public class BookLibraryService extends AbstractService<BookInLibrary> {
    private final BookArchivingMapper bookArchivingMapper;
    private final ServiceFactory serviceFactory;
    private final RepositoryFactory repositoryFactory;
    private final PageUtility pageUtility;
    private final HibernateUnproxyUtility hibernateUnproxyUtility;

    public BookLibraryService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory,
                              BookArchivingMapper bookArchivingMapper, ServiceFactory serviceFactory, HibernateUnproxyUtility hibernateUnproxyUtility) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
        this.bookArchivingMapper = bookArchivingMapper;
        this.serviceFactory = serviceFactory;
        this.repositoryFactory = repositoryFactory;
        this.pageUtility = pageUtility;
        this.hibernateUnproxyUtility = hibernateUnproxyUtility;
    }

    @Override
    @Transactional(value = "libraryTransactionManager", rollbackFor = Exception.class)
    public BookArchived toArchive(Long id) {
        BookInLibrary bookInLibraryForArchiving = read(id);
        BookArchived bookArchived = bookArchivingMapper.map(bookInLibraryForArchiving);
        bookArchived.setId(null);
        bookArchived.setDateOfArchived(Calendar.getInstance());
        bookArchived = (BookArchived) serviceFactory.get(BookArchived.class).save(bookArchived);
        serviceFactory.get(BookInLibrary.class).delete(bookInLibraryForArchiving.getId());
        try {
            serviceFactory.get(Logging.class).save(new Logging("Book " +
                    bookInLibraryForArchiving.getId() + " " + bookInLibraryForArchiving.getName() + " archived successfully"));
        } catch (Exception e) {
            log.error(e);
        }
        return bookArchived;
    }

    @Override
    @Transactional(value = "libraryTransactionManager")
    public BookInLibrary read(Long id) {//filtering deleted related entities
        BookInLibrary a = super.read(id);
        if (a == null) {
            return null;
        }
        if (a.getAuthorList() != null && !a.getAuthorList().isEmpty()) {
            a.setAuthorList(a.getAuthorList().stream().filter(b -> !b.isDeleted()).collect(Collectors.toList()));
        }
        if (a.getPublisher() != null && a.getPublisher().isDeleted()) {
            a.setPublisher(null);
        }
        return a;
    }

    public List<BookInLibrary> readList(Pageable pageable) {
        List<BookInLibrary> listWithDeletedEntities = repositoryFactory.get(BookInLibrary.class).findPageable(pageable);
        listWithDeletedEntities.stream().forEach(bookInLibrary -> {
            if (bookInLibrary.getPublisher() != null && bookInLibrary.getPublisher().isDeleted()) {
                bookInLibrary.setPublisher(null);
            }
            if (bookInLibrary.getAuthorList() != null) {
                bookInLibrary.setAuthorList(bookInLibrary.getAuthorList().stream().filter(author -> !author.isDeleted()).collect(Collectors.toList()));
            }
        });
        return listWithDeletedEntities;
    }
}
