package ru.laptseu.libararyapp.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "library_books")
public class BookInLibrary extends Book {

    @Column(name = "section_number")
    private int sectionNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Author> authorList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}
