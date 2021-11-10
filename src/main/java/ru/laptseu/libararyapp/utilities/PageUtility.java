package ru.laptseu.libararyapp.utilities;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.services.ServiceFactory;

@Component
@Getter
public class PageUtility {

    private final int numberOfEntitiesPerPage;
    private final ServiceFactory serviceFactory;

    public PageUtility(@Value("${app.pages.entities-per-page}") int numberOfEntitiesPerPage, ServiceFactory serviceFactory) {
        this.numberOfEntitiesPerPage = numberOfEntitiesPerPage;
        this.serviceFactory = serviceFactory;
    }


    public Pageable getPageable(int pageNum) {
        return PageRequest.of(pageNum - 1, numberOfEntitiesPerPage, Sort.by("id").descending());
    }

    public boolean getIsFullPage(int size, int page) {
        return size < numberOfEntitiesPerPage;
    }

    public int getExPageNum(int page) {
        if (page == 1) {
            return page;
        } else {
            return page - 1;
        }
    }

    public int getNextPageNum(int size, int page) {
        if (size < numberOfEntitiesPerPage) {
            return page;
        } else {
            return page + 1;
        }
    }
}
