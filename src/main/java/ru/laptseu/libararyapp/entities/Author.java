package ru.laptseu.libararyapp.entities;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;

import javax.persistence.Entity;
import javax.persistence.*;
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
    @ManyToMany(mappedBy = "authorList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BookInLibrary> bookList;

    @Override
    public String toString() {
        String fn;
        String sn;
        String by;
        String dy;

        if (firstName != null) {
            fn = firstName;
        } else {
            fn = "...";
        }
        if (secondName != null) {
            sn = secondName;
        } else {
            sn = "";
        }

        if (birthYear == null) {
            by = "...";
        } else if (birthYear < 0) {
            by = birthYear + "BC";
        } else {
            by = birthYear.toString();
        }
        if (deathYear == null) {
            dy = "...";
        } else if (deathYear < 0) {
            dy = deathYear + "BC";
        } else {
            dy = deathYear.toString();
        }
        return fn + " " + sn + " (" + by + "-" + dy + ")";
    }
}
