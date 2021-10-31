package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public  interface AuthorMapper extends FrontMapper<Author, AuthorDto> {
    Author map(AuthorDto authorDto);

    AuthorDto map(Author author);
}
