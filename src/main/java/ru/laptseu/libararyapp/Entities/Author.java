package ru.laptseu.libararyapp.Entities;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.Books.Book;
import ru.laptseu.libararyapp.Entities.Books.LibraryBook;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Author extends EntityWithLongId {
    private String firstName;
    private String secondName;
    private Integer birthDate;
    private Integer deathDate;
    @ManyToMany(mappedBy = "authorList",fetch = FetchType.EAGER)
    private List<LibraryBook> bookList;
}
