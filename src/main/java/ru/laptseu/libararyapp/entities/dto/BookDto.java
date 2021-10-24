package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto extends EntityDto {
    private Long id;
    private String name;
    private String description;
    private int sectionNumber;
    private int yearOfPublishing;
}
