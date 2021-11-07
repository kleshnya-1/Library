package ru.laptseu.libararyapp.models.dto.simpleDto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.models.dto.EntityDto;

@Getter
@Setter
public class AuthorSimpleDto extends EntityDto {
    private Long id;
    private String firstName;
    private String secondName;
}
