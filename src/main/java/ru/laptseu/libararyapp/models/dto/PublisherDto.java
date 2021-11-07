package ru.laptseu.libararyapp.models.dto;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.models.dto.simpleDto.BookSimpleDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class PublisherDto extends EntityDto {
    @Positive
    private Long id;

    @NotBlank(message = "Имя для издателя обязательно")
    private String name;

    @NotBlank(message = "Адрес для издетеля обязателен")
    private String address;

    @NotBlank(message = "Номер для издателя обязателен")
    private String phoneNumber;

    private List<BookSimpleDto> bookList;
}
