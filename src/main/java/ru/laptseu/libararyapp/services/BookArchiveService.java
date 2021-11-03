package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.LoggingEntity;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
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
    public BookInLibrary fromArchive(BookArchived bookArchived) {
        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
        bookInLibrary.setId(null);
        List<Author> newAuthorList = new ArrayList<>();
        bookInLibrary.getAuthorList().stream().forEach(author -> newAuthorList.add((Author) serviceFactory.get(Author.class).read(author.getId())));// TODO: 02.11.2021 много запросов
        bookInLibrary.setAuthorList(newAuthorList);
        Publisher publisherByIdFromArchivedBook = (Publisher) serviceFactory.get(Publisher.class).read(bookInLibrary.getPublisher().getId());
        bookInLibrary.setPublisher(publisherByIdFromArchivedBook);
        serviceFactory.get(BookInLibrary.class).save(bookInLibrary);
        serviceFactory.get(BookArchived.class).delete(bookArchived.getId());
        try {
            serviceFactory.get(LoggingEntity.class).save(new LoggingEntity("Book " + bookArchived.getId() + " " + bookArchived.getName() + " unarchived successfully"));
        } catch (Exception e) {
            log.error(e);
        }
        return bookInLibrary;
    }
}
