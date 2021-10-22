package ru.laptseu.libararyapp.entities.books;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Publisher;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "library_books")
public class BookInLibrary extends Book {
    private int sectionNumber;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Author> authorList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}
