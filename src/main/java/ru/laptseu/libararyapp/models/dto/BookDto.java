package ru.laptseu.libararyapp.models.dto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.models.dto.simpleDto.AuthorSimpleDto;
import ru.laptseu.libararyapp.models.dto.simpleDto.PublisherSimpleDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class BookDto extends EntityDto {
    @Positive
    private Long id;

    @NotBlank(message = "В библиотеке каждая книга должна иметь хотя бы имя")
    private String name;

    private List<AuthorSimpleDto> authorList;
    private String[] authorArrayForHtml;
    private String description;
    private Integer sectionNumber;
    private Integer yearOfPublishing;
    private PublisherSimpleDto publisherSimpleDto;
}
