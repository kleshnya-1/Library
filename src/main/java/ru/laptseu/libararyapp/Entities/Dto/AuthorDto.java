package ru.laptseu.libararyapp.Entities.Dto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.EntityWithLongId;

import javax.persistence.Entity;
import java.util.List;


@Getter
@Setter
public class AuthorDto  {
    private Long id;
    private String firstName;
    private String secondName;
    private Integer birthDate;
    private Integer deathDate;
    private List<BookDto> bookList;
}
