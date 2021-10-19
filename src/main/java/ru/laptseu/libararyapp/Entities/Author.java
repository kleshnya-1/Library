package ru.laptseu.libararyapp.Entities;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.Books.Book;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
public class Author extends EntityWithLongId{
    private String firstName;
    private String secondName;
    private Integer birthDate;
    private Integer deathDate;
    private List<Book> bookList;
}
