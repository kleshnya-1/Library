package ru.laptseu.libararyapp.repositories.library;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookLibraryRepository extends AbstractRepository<BookInLibrary> {

    @Query("select b from BookInLibrary b left join fetch b.publisher left join fetch b.authorList where b.isDeleted = false")
    List<BookInLibrary> readAllByIsDeletedFalse(Pageable pageable);

    @Query("select b from BookInLibrary b left join fetch b.publisher left join fetch b.authorList where b.isDeleted = false")
    List<BookInLibrary> readAllByIsDeletedFalse();

    @Query("select b from BookInLibrary b left join fetch b.publisher left join fetch b.authorList where b.id = ?1 and b.isDeleted = false")
    Optional<BookInLibrary> readByIdAndIsDeletedFalse(Long id);


}
