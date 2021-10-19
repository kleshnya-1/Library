package ru.laptseu.libararyapp.Entities.Books;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
public class ArchivedBook extends Book{
    private List<Integer> authorList;
}
