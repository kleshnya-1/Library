package ru.laptseu.libararyapp.Entities;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.Books.Book;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
public class Publisher extends EntityWithLongId {
    private String name;
    private String address;
    private String phoneNumber;
    private List<Book> bookList;
}
