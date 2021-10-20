package ru.laptseu.libararyapp.repositories.library;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

//@EntityScan("ru.laptseu.libararyapp.entities")
public interface AuthorRepository extends AbstractRepository<Author> {

}
