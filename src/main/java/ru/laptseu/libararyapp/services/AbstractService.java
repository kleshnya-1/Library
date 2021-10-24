package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.EntityWithLongId;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

import javax.naming.OperationNotSupportedException;

@RequiredArgsConstructor
@Getter
public abstract class AbstractService<T extends EntityWithLongId> {

    private final RepositoryFactory repositoryFactory;

    abstract Class getEntityClass();

    public T save(T entity) {
        T savedEntity = (T) repositoryFactory.get(getEntityClass()).save(entity);
        repositoryFactory.get(Entity.class).save(getEntityClass().getSimpleName() + " " + savedEntity.getId() + " saved");
        return savedEntity;
    }

    public T read(Long id) {
        T entity = (T) repositoryFactory.get(getEntityClass()).findById(id).orElse(null);//todo
        return entity;
    }

    public T update(T entity) {
        return (T) repositoryFactory.get(getEntityClass()).save(entity);
    }

    public void delete(Long id) {
        repositoryFactory.get(getEntityClass()).deleteById(id);
        repositoryFactory.get(Entity.class).save(getEntityClass().getSimpleName() + " " + id + " deleted");
    }

    abstract public BookArchived toArchive(BookInLibrary bookInLibrary) throws OperationNotSupportedException;
     public BookArchived toArchive(Long id) throws OperationNotSupportedException{
         return toArchive((BookInLibrary) read(id));
     }

    abstract public BookInLibrary fromArchive(BookArchived bookArchived) throws OperationNotSupportedException;
     public BookInLibrary fromArchive(Long id) throws OperationNotSupportedException{
         return  fromArchive((BookArchived) read(id));
     }

}
