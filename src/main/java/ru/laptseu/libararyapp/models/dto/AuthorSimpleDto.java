package ru.laptseu.libararyapp.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class AuthorSimpleDto extends EntityDto {
    @Positive
    private Long id;

    private String firstName;
    private String secondName;
}
