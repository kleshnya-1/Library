package ru.laptseu.libararyapp.entities.books;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Getter
@Setter
public class ArchivedBook extends Book {
//    @OneToOne
//    @JoinColumn(name = "libraryBook_id")
//    private LibraryBook libraryBook;

 //   private List<Integer> authorList;

}
