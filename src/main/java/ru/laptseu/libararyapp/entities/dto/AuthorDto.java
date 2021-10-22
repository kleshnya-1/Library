package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthorDto {
    private Long id;
    private String firstName;
    private String secondName;
    private Integer birthDate;
    private Integer deathDate;
}
