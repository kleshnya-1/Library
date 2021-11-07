package ru.laptseu.libararyapp.mappers.frontMappers.simple;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMapper;
import ru.laptseu.libararyapp.models.dto.simpleDto.BookSimpleDto;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;

@Component
@Mapper(componentModel = "spring")
public interface BookSimpleMapper extends FrontMapper<BookInLibrary, BookSimpleDto> {
}
