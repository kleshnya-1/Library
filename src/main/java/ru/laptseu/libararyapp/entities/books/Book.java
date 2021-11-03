package ru.laptseu.libararyapp.entities.books;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.EntityWithId;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Book extends EntityWithId {
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private Integer yearOfPublishing;
}
