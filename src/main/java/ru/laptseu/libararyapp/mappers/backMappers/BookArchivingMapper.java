package ru.laptseu.libararyapp.mappers.backMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;

@Component
@Mapper(componentModel = "spring")//(uses = {BankMapper.class, ClientMapper.class})
public interface BookArchivingMapper {

    @Mapping(source = "id", target = "id")
    Author map(Long id);

    default Long map(Author author) {
        return author.getId();
    }// TODO: 27.10.2021 mapping

    @Mapping(source = "publisher", target = "publisher.id")
    BookInLibrary map(BookArchived archivedBook);

    // TODO: 23.10.2021 null mapping id
    @Mapping(source = "publisher.id", target = "publisher")
    BookArchived map(BookInLibrary libraryBook);

}
