package ru.laptseu.libararyapp.Entities.Dto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.EntityWithLongId;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
public class PublisherDto extends EntityWithLongId {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private List<BookDto> bookList;
}
