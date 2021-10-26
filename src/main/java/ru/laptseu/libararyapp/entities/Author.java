package ru.laptseu.libararyapp.entities;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "authors")
public class Author extends EntityWithLongId {
    private String firstName;
    private String secondName;
    private Integer birthYear;
    private Integer deathYear;
    @ManyToMany(mappedBy = "authorList", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<BookInLibrary> bookList;
}
