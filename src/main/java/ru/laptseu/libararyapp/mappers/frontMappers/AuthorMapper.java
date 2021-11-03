package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;

@Component
@Mapper(componentModel = "spring")
public interface AuthorMapper extends FrontMapper<Author, AuthorDto> {
}
