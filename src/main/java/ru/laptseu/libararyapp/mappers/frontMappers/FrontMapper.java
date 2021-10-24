package ru.laptseu.libararyapp.mappers.frontMappers;

import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.dto.EntityDto;

public interface FrontMapper<T extends Entity> {

    T map(EntityDto entityDto);

    EntityDto map(T entity);

}
