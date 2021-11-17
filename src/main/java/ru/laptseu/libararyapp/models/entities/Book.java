package ru.laptseu.libararyapp.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Book extends EntityWithId {
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "year_of_publishing")
    private Integer yearOfPublishing;
}
