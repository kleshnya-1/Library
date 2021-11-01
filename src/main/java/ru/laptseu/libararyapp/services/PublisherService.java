package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

@Getter
@Setter
@Service

public class PublisherService extends AbstractService<Publisher> {

    public PublisherService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
    }

}
