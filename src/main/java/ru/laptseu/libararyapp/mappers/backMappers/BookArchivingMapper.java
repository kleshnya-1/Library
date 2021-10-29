package ru.laptseu.libararyapp.mappers.backMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.services.AuthorService;

import java.util.List;

@Component
@Mapper(componentModel = "spring")//(uses = {BankMapper.class, ClientMapper.class})
public abstract class BookArchivingMapper {

    @Autowired
    AuthorService authorService;

    @Mapping(source = "id", target = "id")
    public  Author mapAuthor(Long id){
        return authorService.read(id);
    }

    public Long mapAuthor(Author author) {
        return author.getId();
    }// TODO: 27.10.2021 mapping

    @Mapping(source = "publisher", target = "publisher.id")
    public abstract BookInLibrary map(BookArchived archivedBook);

    // TODO: 23.10.2021 null mapping id
    @Mapping(source = "publisher.id", target = "publisher")
  public abstract   BookArchived map(BookInLibrary libraryBook);

    @Mapping(source = "publisher", target = "publisher.id")
    public abstract List<BookInLibrary> map(List<BookArchived> archivedBook);

    // TODO: 23.10.2021 null mapping id
    @Mapping(source = "publisher.id", target = "publisher")
  public abstract   List<BookArchived> mapL(List<BookInLibrary> libraryBook);
}
