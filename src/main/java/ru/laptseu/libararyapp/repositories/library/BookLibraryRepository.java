package ru.laptseu.libararyapp.repositories.library;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface BookLibraryRepository extends AbstractRepository<BookInLibrary> {

    List<BookInLibrary> readAllByIsDeletedFalseAndAuthorList_Id(Long id );

    List<BookInLibrary> readAllByIsDeletedFalseAndPublisher_Id(Long id );

    @Override
    default List<BookInLibrary> findByAuthorId(Long id){
       return readAllByIsDeletedFalseAndAuthorList_Id(id);
    }
    @Override
    default List<BookInLibrary> findByPublisherId(Long id){
       return readAllByIsDeletedFalseAndPublisher_Id(id);
    }
}
