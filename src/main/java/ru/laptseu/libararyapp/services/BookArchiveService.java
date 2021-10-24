package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.LoggingRepository;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class BookArchiveService extends AbstractService<BookArchived> {
    private final BookArchivingMapper bookArchivingMapper;
    private final ServiceFactory serviceFactory;
    private final LoggingRepository loggingRepository;//directly to repository. is it ok? because message in String can't be saved in <T extends Entity>
    Class entityClass = BookArchived.class;

    public BookArchiveService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory, BookArchivingMapper bookArchivingMapper, ServiceFactory serviceFactory, LoggingRepository loggingRepository) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
        this.bookArchivingMapper = bookArchivingMapper;
        this.serviceFactory = serviceFactory;
        this.loggingRepository = loggingRepository;
    }

    @Transactional(value = "archiveTransactionManager", rollbackFor = Exception.class)
    public BookInLibrary fromArchive(BookArchived bookArchived) {
        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
        List<Author> newAuthorList = new ArrayList<>();
        bookInLibrary.getAuthorList().stream().forEach(author -> newAuthorList.add((Author) serviceFactory.get(Author.class).read(author.getId())));
        bookInLibrary.setAuthorList(newAuthorList);

        Publisher publisherByIdFromArchivedBook = (Publisher) serviceFactory.get(Publisher.class).read(bookInLibrary.getPublisher().getId());
        bookInLibrary.setPublisher(publisherByIdFromArchivedBook);
        serviceFactory.get(bookInLibrary.getClass()).save(bookInLibrary);
        serviceFactory.get(bookArchived.getClass()).delete(bookArchived.getId());
        loggingRepository.save("Book " + bookArchived.getId() + " " + bookArchived.getName() + " unarchived successfully");
        return bookInLibrary;
    }
}
