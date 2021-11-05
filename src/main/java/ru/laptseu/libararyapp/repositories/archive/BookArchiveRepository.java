package ru.laptseu.libararyapp.repositories.archive;

import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

@Repository
public interface BookArchiveRepository extends AbstractRepository<BookArchived> {
}
