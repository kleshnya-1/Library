package ru.laptseu.libararyapp.mappers.frontMappers.simple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMapper;
import ru.laptseu.libararyapp.models.dto.simpleDto.AuthorSimpleDto;
import ru.laptseu.libararyapp.models.entities.Author;

@Component
@Mapper(componentModel = "spring")
public interface AuthorSimpleMapper extends FrontMapper<Author, AuthorSimpleDto> {
    @Mapping(source = "firstName", target = "firstName", defaultValue = "...")
    @Mapping(source = "secondName", target = "secondName", defaultValue = "")
    AuthorSimpleDto map(Author entity);
}
