package ru.laptseu.libararyapp.entities.books;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Publisher;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "archived_books")
public class BookArchived extends Book {
    private Calendar dateOfArchived;

  //  @ElementCollection
//    private List<Integer> authorIdList;

    private Integer publisherId;
}
