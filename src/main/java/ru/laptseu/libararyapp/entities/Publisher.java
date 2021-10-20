package ru.laptseu.libararyapp.entities;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.books.LibraryBook;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Publisher extends EntityWithLongId {
    private String name;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryBook> bookList;
}
