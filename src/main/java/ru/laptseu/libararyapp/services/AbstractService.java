package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import ru.laptseu.libararyapp.entities.EntityWithLongId;
import ru.laptseu.libararyapp.entities.LoggingEntity;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Getter
public abstract class AbstractService<T extends EntityWithLongId> {
    private final RepositoryFactory repositoryFactory;
    private final PageUtility pageUtility;
    private final FrontMappersFactory frontMappersFactory;

    Class<? extends EntityWithLongId> getEntityClass() {
        return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public T save(T entity) {
        int saveCounter = 0;
        T savedEntity = null;
        while (saveCounter <= 10) {//  spring starts to count from last saved id by itself(!).
            try {
                savedEntity = (T) repositoryFactory.get(getEntityClass()).save(entity);
                repositoryFactory.get(LoggingEntity.class).save(new LoggingEntity(getEntityClass().getSimpleName() + " " + savedEntity.getId() + " saved"));
                break;
            } catch (DataIntegrityViolationException e) {
                log.error(e);
            }
        }
        return savedEntity;
    }

    public T read(Long id) {
        return (T) repositoryFactory.get(getEntityClass()).findById(id).orElse(null);
    }

    public List<T> read() {
        return repositoryFactory.get(getEntityClass()).findAll();
    }

    public T update(T entity) {
        return (T) repositoryFactory.get(getEntityClass()).save(entity);
    }

    public void delete(Long id) {
        repositoryFactory.get(getEntityClass()).deleteById(id);
        repositoryFactory.get(LoggingEntity.class).save(new LoggingEntity(getEntityClass().getSimpleName() + " " + id + " deleted"));
    }

    public BookArchived toArchive(BookInLibrary bookInLibrary) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public BookArchived toArchive(Long id) throws OperationNotSupportedException {
        return toArchive((BookInLibrary) read(id));
    }

    public BookInLibrary fromArchive(BookArchived bookArchived) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public BookInLibrary fromArchive(Long id) throws OperationNotSupportedException {
        return fromArchive((BookArchived) read(id));
    }

    public List<T> readList(Integer page) {
        Pageable pageable = pageUtility.getPageable(page);
        return repositoryFactory.get(getEntityClass()).findPageable(pageable);
    }

    public List<T> readBooksByAuthor(Long id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public List<T> readBooksByPublisher(Long id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
