package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.EntityWithLongId;

@Getter
@Setter
public class BookDto extends EntityWithLongId {
    private Long id;
    private String name;
    private String description;
    private int sectionNumber;
    private int yearOfPublishing;
 //   private PublisherDto publisher;

}
