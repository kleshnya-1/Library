package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.entities.dto.BookDto;

@Component
@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public  interface BookMapper extends FrontMapper<BookInLibrary, BookDto> {
      BookInLibrary map(BookDto bookDto);


       BookDto map(BookInLibrary bookInLibrary);
}
