package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.entities.dto.BookDto;
import ru.laptseu.libararyapp.entities.dto.EntityDto;

@Component
@Mapper(componentModel = "spring")
public abstract class BookMapper implements FrontMapper<BookInLibrary> {
    public abstract BookInLibrary map(BookDto bookDto);

    public BookInLibrary map(EntityDto bookDto) {
        BookDto bookDto1 = (BookDto) bookDto;
        return map(bookDto1);
    }

    public abstract BookDto map(BookInLibrary bookInLibrary);
}
