package ru.laptseu.libararyapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.laptseu.libararyapp.entities.EntityWithLongId;

import java.util.List;

public interface AbstractRepository<T extends EntityWithLongId> extends PagingAndSortingRepository<T , Long> {

    List<T> readAllByIsDeletedFalse(Pageable pageable);

    List<T> readAllByIsDeletedFalse();


    T readByIdAndIsDeletedFalse(int id);

    default Page<T> findPageAll(Pageable pageable) {
        return (Page<T>) readAllByIsDeletedFalse(pageable);
    }

    default List<T> findAll() {
        return readAllByIsDeletedFalse();
    }

    default T findById(int id) {
        return readByIdAndIsDeletedFalse(id);
    }

    default void deleteById(int id) {
        T ent = findById(id);
        if (ent != null) {
            ent.setDeleted(true);
            save(ent);
        }
    }

//    default T save(T obj){
//
//    }
}
