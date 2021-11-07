package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.stream.Collectors;

@Getter
@Setter
@Service

public class PublisherService extends AbstractService<Publisher> {

    public PublisherService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
    }

    @Override
    @Transactional(value = "libraryTransactionManager")
    public Publisher read(Long id) {
        Publisher a = super.read(id);
        if (a == null) {
            return null;
        }
        if (a.getBookList() != null) {
            a.setBookList(a.getBookList().stream().filter(b -> b.isDeleted() == false).collect(Collectors.toList()));
        }
        return a;
    }
    }
