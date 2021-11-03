package ru.laptseu.libararyapp.repositories.library;

import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

@Repository
public interface BookLibraryRepository extends AbstractRepository<BookInLibrary> {
}
