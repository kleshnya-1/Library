package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.dto.AuthorDto;
import ru.laptseu.libararyapp.models.entities.Author;

@Component
@Mapper(componentModel = "spring")
public interface AuthorMapper extends FrontMapper<Author, AuthorDto> {

    @Mapping(source = "firstName", target = "firstName", defaultValue = "...")
    @Mapping(source = "secondName", target = "secondName", defaultValue = "")
    AuthorDto map(Author entity);
}
