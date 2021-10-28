package ru.laptseu.libararyapp.utilities;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PageUtility {

    private final int numberOfEntitiesPerPage;// TODO: 24.10.2021 move to properties

    public PageUtility(@Value("${app.pages.entities-per-page}") int numberOfEntitiesPerPage) {
        this.numberOfEntitiesPerPage = numberOfEntitiesPerPage;
    }


    public Pageable getPageable(int pageNum) {
        return PageRequest.of(pageNum - 1, numberOfEntitiesPerPage);
    }

    public int getExPageNum(int page) {
        if (page == 1) {
            return page;
        } else {
            return page - 1;
        }
    }

    public int getNextPageNum(int size, int page) {
        if (page >= (size / numberOfEntitiesPerPage + 1)) {
            return page;
        } else {
            return page + 1;
        }
    }
}
