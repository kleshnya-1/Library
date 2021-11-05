package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.models.dto.PublisherDto;

@Component
@Mapper(componentModel = "spring")
public interface PublisherMapper extends FrontMapper<Publisher, PublisherDto> {
}
