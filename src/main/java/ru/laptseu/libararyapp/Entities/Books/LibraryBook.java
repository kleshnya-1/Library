package ru.laptseu.libararyapp.Entities.Books;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.Author;

import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.util.List;

@Entity
@Getter
@Setter
public class LibraryBook extends Book{
    private List<Author> authorList;
}
