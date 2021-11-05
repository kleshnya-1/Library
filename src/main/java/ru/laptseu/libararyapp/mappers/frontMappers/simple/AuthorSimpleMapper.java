package ru.laptseu.libararyapp.mappers.frontMappers.simple;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMapper;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.dto.AuthorSimpleDto;

@Component
@Mapper(componentModel = "spring")
public interface AuthorSimpleMapper extends FrontMapper<Author, AuthorSimpleDto> {
}
