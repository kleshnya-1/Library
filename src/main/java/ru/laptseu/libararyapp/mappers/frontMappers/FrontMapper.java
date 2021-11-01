package ru.laptseu.libararyapp.mappers.frontMappers;

import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.dto.EntityDto;

import java.util.List;

public interface FrontMapper<E extends Entity, D extends EntityDto> {

    E map(D entityDto);

    D map(E entity);

    List<E> mapFromDtoList(List<D> entityDtoList);

    List<D> map(List<E> entityList);
}
