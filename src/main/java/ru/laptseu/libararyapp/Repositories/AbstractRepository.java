package ru.laptseu.libararyapp.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.laptseu.libararyapp.Entities.EntityWithLongId;

import java.util.List;

public interface AbstractRepository<T extends EntityWithLongId> extends PagingAndSortingRepository<T, Long> {
    //  Pageable pageable = PageRequest.of(0, 20);
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
        } else {
            //todo
        }
    }
//    default void delete(T ent){
//        if (findById(ent.getId())!=null){
//            ent.setDeleted(true);
//            save(ent);
//        } else {
//
////todo
//        }
//
//    }

}
