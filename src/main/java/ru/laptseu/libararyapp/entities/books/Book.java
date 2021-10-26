package ru.laptseu.libararyapp.entities.books;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.EntityWithLongId;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Book extends EntityWithLongId {
    private String name;
    private String description;
    private Integer yearOfPublishing;
}
