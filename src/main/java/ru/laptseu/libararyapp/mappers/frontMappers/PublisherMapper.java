package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.dto.PublisherDto;

@Component
@Mapper(componentModel = "spring")
public interface PublisherMapper extends FrontMapper<Publisher, PublisherDto> {
}
