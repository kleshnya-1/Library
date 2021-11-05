package ru.laptseu.libararyapp.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PublisherSimpleDto extends EntityDto {
    @Positive
    private Long id;

    @NotEmpty
    private String name;
}
