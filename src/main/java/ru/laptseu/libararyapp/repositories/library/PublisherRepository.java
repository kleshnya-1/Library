package ru.laptseu.libararyapp.repositories.library;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends AbstractRepository<Publisher> {

    @Query("select p from Publisher p left join fetch p.bookList where p.isDeleted = false")
    List<Publisher> readAllByIsDeletedFalse(Pageable pageable);

    @Query("select p from Publisher p where p.isDeleted = false")
    List<Publisher> readAllByIsDeletedFalse();

    @Query("select p from Publisher p left join fetch p.bookList where p.id = ?1 and p.isDeleted = false")
    Optional<Publisher> readByIdAndIsDeletedFalse(Long id);
}
