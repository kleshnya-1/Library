package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.models.dto.BookDto;

@Component
@Mapper(componentModel = "spring", uses = {AuthorMapper.class, PublisherMapper.class})
public interface BookMapper extends FrontMapper<BookInLibrary, BookDto> {
    @Mapping(source = "publisherDto", target = "publisher")
    BookInLibrary map(BookDto bookDto);

    @Mapping(source = "publisher", target = "publisherDto")
    BookDto map(BookInLibrary bookInLibrary);
}
