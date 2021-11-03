package ru.laptseu.libararyapp.entities;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "publishers")
public class Publisher extends EntityWithId {
    private String name;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BookInLibrary> bookList;
}
