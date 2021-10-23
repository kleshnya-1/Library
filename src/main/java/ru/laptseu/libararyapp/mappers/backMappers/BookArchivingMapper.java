package ru.laptseu.libararyapp.mappers.backMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.services.BookArchiveService;
import ru.laptseu.libararyapp.services.BookLibraryService;

@Component
@Mapper(componentModel = "spring")//(uses = {BankMapper.class, ClientMapper.class})
public abstract class BookArchivingMapper {
    @Autowired
    BookLibraryService bookLibraryService;
    @Autowired
    BookArchiveService bookArchiveService;


    public abstract BookInLibrary map(BookArchived archivedBook);


   // @Mapping(source = "publisher.id", target = "publisher")
    public abstract BookArchived map(BookInLibrary libraryBook);
}
