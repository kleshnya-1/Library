package ru.laptseu.libararyapp.Services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.Entities.Books.ArchivedBook;
import ru.laptseu.libararyapp.Entities.Books.LibraryBook;
import ru.laptseu.libararyapp.Entities.EntityWithLongId;
import ru.laptseu.libararyapp.Mappers.BookArchivingMapper;
import ru.laptseu.libararyapp.Repositories.RepositoryFactory;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class AbstractService<T extends EntityWithLongId> {
    protected final BookArchivingMapper bookArchivingMapper;
    private final RepositoryFactory repositoryFactory;

    abstract Class getEntityClass();

    public T save(T entity) {
        var savedEntity = (T) repositoryFactory.get(getEntityClass()).save(entity);
        repositoryFactory.get(String.class).save(getEntityClass().getSimpleName() + " " + savedEntity.getId() + " saved");
        return savedEntity;
    }

    public T read(Long id) {
        T entity = (T) repositoryFactory.get(getEntityClass()).findById(id).orElse(null);//todo
        return entity;
    }

    public void delete(Long id) {
        repositoryFactory.get(getEntityClass()).deleteById(id);
        repositoryFactory.get(String.class).save(getEntityClass().getSimpleName() + " " + id + " deleted");
    }

    @Transactional(rollbackFor = Exception.class)
    public ArchivedBook toArchive(LibraryBook libraryBook) {
        ArchivedBook archivedBook = bookArchivingMapper.map(libraryBook);
        repositoryFactory.get(archivedBook.getClass()).save(archivedBook);
        repositoryFactory.get(libraryBook.getClass()).deleteById(libraryBook.getId());
        repositoryFactory.get(String.class).save("Book " + libraryBook.getId() + " " + libraryBook.getName() + " archived successfully");
        return archivedBook;
    }

    @Transactional(rollbackFor = Exception.class)
    public LibraryBook fromArchive(ArchivedBook archivedBook) {
        LibraryBook libraryBook = bookArchivingMapper.map(archivedBook);
        repositoryFactory.get(libraryBook.getClass()).save(libraryBook);
        repositoryFactory.get(archivedBook.getClass()).deleteById(archivedBook.getId());
        repositoryFactory.get(String.class).save("Book " + archivedBook.getId() + " " + archivedBook.getName() + " unarchived successfully");
        return libraryBook;
    }
}
