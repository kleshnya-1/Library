package ru.laptseu.libararyapp.mappers.frontMappers;

import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.dto.EntityDto;

import java.util.List;
import java.util.stream.Collectors;

public interface FrontMapper<T extends Entity> {

    T map(EntityDto entityDto);

    EntityDto map(T entity);

//    default List<T> map(List<EntityDto>  entityDto){
//       return  entityDto.stream().map(entityDto1 -> map(entityDto1)).collect(Collectors.toList());
//   }

   default List<EntityDto> map(List<T>  entityDto){
       return  entityDto.stream().map(entityDto1 -> map(entityDto1)).collect(Collectors.toList());
   }
}
