package ru.laptseu.libararyapp.mappers.frontMappers;

import ru.laptseu.libararyapp.models.entities.EntityWithId;
import ru.laptseu.libararyapp.models.dto.EntityDto;

import java.util.List;

public interface FrontMapper<E extends EntityWithId, D extends EntityDto> {

    E map(D entityDto);

    D map(E entity);

    List<E> mapFromDtoList(List<D> entityDtoList);

    List<D> map(List<E> entityList);
}
