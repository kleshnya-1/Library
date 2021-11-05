package ru.laptseu.libararyapp.mappers.backMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.services.AuthorService;
import ru.laptseu.libararyapp.services.PublisherService;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class BookArchivingMapper {

    @Autowired
    AuthorService authorService;
    @Autowired
    PublisherService publisherService;

    @Mapping(source = "id", target = "id")
    public Author mapAuthor(Long id) {
        return authorService.read(id);
    }

    @Mapping(source = "id", target = "id")
    public Publisher mapPublisher(Long id) {
        return publisherService.read(id);
    }

    public Long mapAuthor(Author author) {
        return author.getId();
    }

    public Long mapPublisher(Publisher publisher) {
        return publisher.getId();
    }

    public abstract BookInLibrary map(BookArchived archivedBook);

    public abstract BookArchived map(BookInLibrary libraryBook);

    public abstract List<BookInLibrary> map(List<BookArchived> archivedBook);

    public abstract List<BookArchived> mapL(List<BookInLibrary> libraryBook);
}
