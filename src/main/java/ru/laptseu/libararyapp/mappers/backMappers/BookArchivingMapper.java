package ru.laptseu.libararyapp.mappers.backMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.services.AuthorService;
import ru.laptseu.libararyapp.services.PublisherService;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface BookArchivingMapper {


    @Mapping(source = "id", target = "id")
     Author mapAuthor(Long id);
//    {
//        return authorService.read(id);
//    }

    @Mapping(source = "id", target = "id")
     Publisher mapPublisher(Long id);
//    {
//        return publisherService.read(id);
//    }

    default Long mapAuthor(Author author) {
        return author.getId();
    }

    default Long mapPublisher(Publisher publisher) {
        if (publisher != null) {
            return publisher.getId();
        } else {
            return null;
        }
    }

     BookInLibrary map(BookArchived archivedBook);

    BookArchived map(BookInLibrary libraryBook);

     List<BookInLibrary> map(List<BookArchived> archivedBook);

    List<BookArchived> mapL(List<BookInLibrary> libraryBook);
}
