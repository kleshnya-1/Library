package ru.laptseu.libararyapp.entities.books;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "archived_books")
public class BookArchived extends Book {
    private Calendar dateOfArchived;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> authorList;

    private Integer publisher;
}
