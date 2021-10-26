package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;
import ru.laptseu.libararyapp.entities.dto.EntityDto;

@Component
@Mapper(componentModel = "spring")
public abstract class AuthorMapper implements FrontMapper<Author> {
    public abstract Author map(AuthorDto authorDto);

    public Author map(EntityDto authorDto) {
        AuthorDto authorDto1 = (AuthorDto) authorDto;
        return map(authorDto1);
    }

    public abstract AuthorDto map(Author author);
}
