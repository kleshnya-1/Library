package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthorDto extends EntityDto {
    private Long id;
    private String firstName;
    private String secondName;
    private Integer birthYear;
    private Integer deathYear;
}
