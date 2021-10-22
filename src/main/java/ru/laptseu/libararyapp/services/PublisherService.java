package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

@Getter
@Setter
@Service

public class PublisherService extends AbstractService<Publisher> {
    Class entityClass = Publisher.class;

    public PublisherService(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
    }
}
