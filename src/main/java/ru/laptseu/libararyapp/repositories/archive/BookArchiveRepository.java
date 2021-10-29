package ru.laptseu.libararyapp.repositories.archive;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface BookArchiveRepository extends AbstractRepository<BookArchived> {

    //@Query("select b from BookArchived b where b.isDeleted = false and b.authorList like %?1%")
    List<BookArchived> readAllByIsDeletedFalseAndAuthorListContains(Long id );

    List<BookArchived> readAllByIsDeletedFalseAndPublisher(Long id );

    @Override
    default List<BookArchived> findByAuthorId(Long id){
        return readAllByIsDeletedFalseAndAuthorListContains(id);
    }
    @Override
    default List<BookArchived> findByPublisherId(Long id){
        return readAllByIsDeletedFalseAndPublisher(id);
    }
}
