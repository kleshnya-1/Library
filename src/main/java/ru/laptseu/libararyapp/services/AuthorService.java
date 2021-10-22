package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

@Getter
@Service
public class AuthorService extends AbstractService<Author> {
    Class entityClass = Author.class;

    public AuthorService(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
    }
}
