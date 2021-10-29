package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.dto.PublisherDto;

@Component
@Mapper(componentModel = "spring"//, uses = AuthorMapper.class
)
public interface PublisherMapper extends FrontMapper<Publisher, PublisherDto> {
    Publisher map(PublisherDto publisherDto);
    PublisherDto map(Publisher publisher);
}
