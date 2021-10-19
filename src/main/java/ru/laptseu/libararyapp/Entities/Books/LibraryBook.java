package ru.laptseu.libararyapp.Entities.Books;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.Author;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class LibraryBook extends Book {
      @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authorList;
}
