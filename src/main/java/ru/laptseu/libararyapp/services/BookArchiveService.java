package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.models.entities.*;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
@Service
public class BookArchiveService extends AbstractService<BookArchived> {
    private final BookArchivingMapper bookArchivingMapper;
    private final ServiceFactory serviceFactory;

    public BookArchiveService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory,
                              BookArchivingMapper bookArchivingMapper, ServiceFactory serviceFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
        this.bookArchivingMapper = bookArchivingMapper;
        this.serviceFactory = serviceFactory;
    }


    @Override
    @Transactional(value = "archiveTransactionManager", rollbackFor = Exception.class)
    public BookInLibrary fromArchive(Long id) {
        BookArchived bookArchived = super.read(id);
        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
        bookInLibrary.setId(null);
        if (bookInLibrary.getPublisher() != null) {
            bookInLibrary.setPublisher((Publisher) serviceFactory.get(Publisher.class).read(bookInLibrary.getPublisher().getId()));
        } else {
            bookInLibrary.setPublisher(null);
        }
        serviceFactory.get(BookInLibrary.class).save(bookInLibrary);
        serviceFactory.get(BookArchived.class).delete(bookArchived.getId());
        try {
            serviceFactory.get(Logging.class).save(new Logging("Book " + bookArchived.getId() + " " + bookArchived.getName() + " unarchived successfully"));
        } catch (Exception e) {
            log.error(e);
        }
        return bookInLibrary;
    }
}
