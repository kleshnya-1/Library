package ru.laptseu.libararyapp.repositories.archive;

import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface BookArchiveRepository extends AbstractRepository<BookArchived> {

    List<BookArchived> readAllByIsDeletedFalseAndAuthorListContains(Long id);

    List<BookArchived> readAllByIsDeletedFalseAndPublisher(Long id);

//    @Override
//    default List<BookArchived> findByAuthorId(Long id) {
//        return readAllByIsDeletedFalseAndAuthorListContains(id);
//    }
//
//    @Override
//    default List<BookArchived> findByPublisherId(Long id) {// TODO: 02.11.2021 hibernate getMetgod ref
//        return readAllByIsDeletedFalseAndPublisher(id);
//    }
}
