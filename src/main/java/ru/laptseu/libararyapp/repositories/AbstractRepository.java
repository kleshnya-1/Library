package ru.laptseu.libararyapp.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.entities.EntityWithId;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbstractRepository<T extends EntityWithId> extends PagingAndSortingRepository<T, Long> {

    List<T> readAllByIsDeletedFalse(Pageable pageable);

    List<T> readAllByIsDeletedFalse();

    Optional<T> readByIdAndIsDeletedFalse(Long id);

    default List<T> findPageable(Pageable pageable) {
        return readAllByIsDeletedFalse(pageable);
    }

    default List<T> findAll() {
        return readAllByIsDeletedFalse();
    }

    default Optional<T> findById(Long id) {
        return readByIdAndIsDeletedFalse(id);
    }

    @Override
    default void deleteById(Long id) {
        T ent = findById(id).orElse(null);
        if (ent != null) {
            ent.setDeleted(true);
            save(ent);
        }
    }
}
