package ru.laptseu.libararyapp.mappers.frontMappers.simple;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMapper;
import ru.laptseu.libararyapp.models.dto.PublisherDto;
import ru.laptseu.libararyapp.models.dto.PublisherSimpleDto;
import ru.laptseu.libararyapp.models.entities.Publisher;

@Component
@Mapper(componentModel = "spring")
public interface PublisherSimpleMapper extends FrontMapper<Publisher, PublisherSimpleDto> {
}
