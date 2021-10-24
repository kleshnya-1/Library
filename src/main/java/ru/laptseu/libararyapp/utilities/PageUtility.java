package ru.laptseu.libararyapp.utilities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageUtility {

    private final int NUMBER_OF_ENTITIES_PER_PAGE = 5;// TODO: 24.10.2021 move to properties

    public Pageable getPageable(int pageNum) {
        return PageRequest.of(pageNum - 1, NUMBER_OF_ENTITIES_PER_PAGE);
    }

}
