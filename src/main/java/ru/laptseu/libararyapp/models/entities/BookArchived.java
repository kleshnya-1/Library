package ru.laptseu.libararyapp.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "archived_books")
public class BookArchived extends Book {

    @Column(name = "date_of_archived")
    private Calendar dateOfArchived;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Long> authorList;

    private Long publisher;
}
