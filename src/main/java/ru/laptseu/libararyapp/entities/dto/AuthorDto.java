package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AuthorDto  {
    private Long id;
    private String firstName;
    private String secondName;
    private Integer birthDate;
    private Integer deathDate;
   // private List<BookDto> bookList;
}
