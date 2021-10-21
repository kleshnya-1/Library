package ru.laptseu.libararyapp.entities.books;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Calendar;
import java.util.List;

@Entity
@Getter
@Setter
public class ArchivedBook extends Book {
private Calendar dateOfArchived;
}
