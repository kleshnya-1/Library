package ru.laptseu.libararyapp.repositories.library;

import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

@Repository
public interface AuthorRepository extends AbstractRepository<Author> {
}
