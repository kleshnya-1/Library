package ru.laptseu.libararyapp.Mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.Entities.Books.ArchivedBook;
import ru.laptseu.libararyapp.Entities.Books.LibraryBook;

@Component
@Mapper(componentModel = "spring")//(uses = {BankMapper.class, ClientMapper.class})
public interface BookArchivingMapper {

    LibraryBook map(ArchivedBook archivedBook);

    ArchivedBook map(LibraryBook libraryBook);
}
