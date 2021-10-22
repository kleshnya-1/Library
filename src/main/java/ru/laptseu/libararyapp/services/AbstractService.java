package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.EntityWithLongId;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;

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

//    @Transactional(rollbackFor = Exception.class)
//    public BookArchived toArchive(BookInLibrary bookInLibrary) {
//        BookArchived bookArchived = bookArchivingMapper.map(bookInLibrary);
//        repositoryFactory.get(bookArchived.getClass()).save(bookArchived);
//        repositoryFactory.get(bookInLibrary.getClass()).deleteById(bookInLibrary.getId());
//        repositoryFactory.get(Entity.class).save("Book " + bookInLibrary.getId() + " " + bookInLibrary.getName() + " archived successfully");
//        return bookArchived;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public BookInLibrary fromArchive(BookArchived bookArchived) {
//        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
//        repositoryFactory.get(bookInLibrary.getClass()).save(bookInLibrary);
//        repositoryFactory.get(bookArchived.getClass()).deleteById(bookArchived.getId());
//        repositoryFactory.get(Entity.class).save("Book " + bookArchived.getId() + " " + bookArchived.getName() + " unarchived successfully");
//        return bookInLibrary;
//    }
}
