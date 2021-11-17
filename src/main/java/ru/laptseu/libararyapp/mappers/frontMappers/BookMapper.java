package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.mappers.frontMappers.simple.AuthorSimpleMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.simple.PublisherSimpleMapper;
import ru.laptseu.libararyapp.models.dto.BookDto;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;

@Component
@Mapper(componentModel = "spring", uses = {AuthorSimpleMapper.class, PublisherSimpleMapper.class})
public interface BookMapper extends FrontMapper<BookInLibrary, BookDto> {
    @Mapping(source = "publisherSimpleDto", target = "publisher")
    BookInLibrary map(BookDto bookDto);

    @Mapping(source = "publisher", target = "publisherSimpleDto")
    BookDto map(BookInLibrary bookInLibrary);
}
