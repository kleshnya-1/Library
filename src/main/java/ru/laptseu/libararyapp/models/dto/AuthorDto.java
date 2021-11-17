package ru.laptseu.libararyapp.models.dto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.models.dto.simpleDto.BookSimpleDto;

import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class AuthorDto extends EntityDto {
    @Positive
    private Long id;

    private String firstName;
    private String secondName;
    private Integer birthYear;
    private Integer deathYear;
    private List<BookSimpleDto> bookList;
}
