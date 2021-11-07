package ru.laptseu.libararyapp.mappers.frontMappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.dto.PublisherDto;
import ru.laptseu.libararyapp.models.entities.Publisher;

@Component
@Mapper(componentModel = "spring")
public interface PublisherMapper extends FrontMapper<Publisher, PublisherDto> {
}
