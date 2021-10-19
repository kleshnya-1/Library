package ru.laptseu.libararyapp.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.laptseu.libararyapp.Entities.Books.ArchivedBook;
import ru.laptseu.libararyapp.Entities.Books.LibraryBook;

@Mapper(componentModel = "spring")//(uses = {BankMapper.class, ClientMapper.class})
public interface BookArchivingMapper  {

    LibraryBook map(ArchivedBook archivedBook);
    ArchivedBook map(LibraryBook libraryBook);
}
