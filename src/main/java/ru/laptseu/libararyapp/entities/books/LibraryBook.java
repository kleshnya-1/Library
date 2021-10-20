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
public class LibraryBook extends Book {
    private int sectionNumber;
    @ManyToMany(mappedBy = "bookList",fetch = FetchType.EAGER)
    private List<Author> authorList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "libraryBook")
//    private ArchivedBook archivedBook;
}
