package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.entities.EntityWithLongId;

import java.util.List;


@Getter
@Setter
public class PublisherDto extends EntityWithLongId {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private List<BookDto> bookList;
}
