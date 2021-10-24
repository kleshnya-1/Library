package ru.laptseu.libararyapp.entities.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PublisherDto extends EntityDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
}
