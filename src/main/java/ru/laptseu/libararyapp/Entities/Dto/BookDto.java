package ru.laptseu.libararyapp.Entities.Dto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.EntityWithLongId;

@Getter
@Setter
public class BookDto extends EntityWithLongId {
    private Long id;
    private String name;
    private String description;
    private int sectionNumber;
    private int yearOfPublishing;
    private PublisherDto publisher;

}
