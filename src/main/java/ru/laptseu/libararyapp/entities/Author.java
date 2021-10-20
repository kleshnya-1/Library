package ru.laptseu.libararyapp.entities;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.books.LibraryBook;

import javax.persistence.CascadeType;
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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<LibraryBook> bookList;
}
