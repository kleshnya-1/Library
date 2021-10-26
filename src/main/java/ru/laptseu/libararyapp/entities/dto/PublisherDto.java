package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;


@Getter
@Setter
public class PublisherDto extends EntityDto {
    @Positive
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;
}
