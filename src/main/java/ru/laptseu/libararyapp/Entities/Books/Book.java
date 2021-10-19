package ru.laptseu.libararyapp.Entities.Books;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.EntityWithLongId;
import ru.laptseu.libararyapp.Entities.Publisher;

@Getter
@Setter
public class Book extends EntityWithLongId {
    private String name;
    private String description;
    private int sectionNumber;
    private int yearOfPublishing;
    private Publisher publisher;
}
