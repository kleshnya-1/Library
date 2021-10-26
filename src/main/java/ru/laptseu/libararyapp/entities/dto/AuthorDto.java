package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class AuthorDto extends EntityDto {
    @Positive
    private Long id;

    private String firstName;
    private String secondName;
    private Integer birthYear;
    private Integer deathYear;
}
