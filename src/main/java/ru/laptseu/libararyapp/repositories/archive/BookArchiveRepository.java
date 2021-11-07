package ru.laptseu.libararyapp.repositories.archive;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookArchiveRepository extends AbstractRepository<BookArchived> {

    @Query("select b from BookArchived b left join fetch b.authorList where b.isDeleted = false")
    List<BookArchived> readAllByIsDeletedFalse(Pageable pageable);

    @Query("select b from BookArchived b left join fetch b.authorList where b.isDeleted = false")
    List<BookArchived> readAllByIsDeletedFalse();

    @Query("select b from BookArchived b left join fetch b.authorList where b.id = ?1 and b.isDeleted = false")
    Optional<BookArchived> readByIdAndIsDeletedFalse(Long id);


}
