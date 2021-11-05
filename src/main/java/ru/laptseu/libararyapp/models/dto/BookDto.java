package ru.laptseu.libararyapp.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class BookDto extends EntityDto {
    @Positive
    private Long id;

    @NotEmpty(message = "it's library and each book must have at least a name")
    private String name;

    private List<AuthorSimpleDto> authorList;
    private String[] authorArrayForHtml;
    private String description;
    private Integer sectionNumber;
    private Integer yearOfPublishing;
    private PublisherDto publisherDto;
    private boolean unknownPublishingYear;
}
