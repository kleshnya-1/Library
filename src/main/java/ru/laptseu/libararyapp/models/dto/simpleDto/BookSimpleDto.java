package ru.laptseu.libararyapp.models.dto.simpleDto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.models.dto.EntityDto;

@Getter
@Setter
public class BookSimpleDto extends EntityDto {
    private Long id;
    private String name;
}
