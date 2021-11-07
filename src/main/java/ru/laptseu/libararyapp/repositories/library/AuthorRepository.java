package ru.laptseu.libararyapp.repositories.library;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends AbstractRepository<Author> {

    @Query("select a from Author a  where a.isDeleted = false")
    List<Author> readAllByIsDeletedFalse(Pageable pageable);

    @Query("select a from Author a  where a.isDeleted = false ")
    List<Author> readAllByIsDeletedFalse();

    @Query("select a from Author a left join fetch a.bookList where a.id = ?1 and a.isDeleted = false")
    Optional<Author> readByIdAndIsDeletedFalse(Long id);


}
