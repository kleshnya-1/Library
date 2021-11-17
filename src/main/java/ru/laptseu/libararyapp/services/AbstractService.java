package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.models.entities.EntityWithId;
import ru.laptseu.libararyapp.models.entities.Logging;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Getter
public abstract class AbstractService<T extends EntityWithId> {
    private final RepositoryFactory repositoryFactory;
    private final PageUtility pageUtility;
    private final FrontMappersFactory frontMappersFactory;

    Class<? extends EntityWithId> getEntityClass() {
        return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public T save(T entity) {
        int saveCounter = 0;
        T savedEntity = null;
        while (saveCounter <= 10) {
            try {// TODO: 02.11.2021 spring starts to count from last saved id by itself(!).
                savedEntity = (T) repositoryFactory.get(getEntityClass()).save(entity);
                repositoryFactory.get(Logging.class).save(new Logging(getEntityClass().getSimpleName() + " " + savedEntity.getId() + " saved"));
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
        repositoryFactory.get(Logging.class).save(new Logging(getEntityClass().getSimpleName() + " " + id + " deleted"));
    }

    public BookArchived toArchive(Long id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public BookInLibrary fromArchive(Long id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }


    public List<T> readList(Pageable pageable) {
        return repositoryFactory.get(getEntityClass()).findPageable(pageable);
    }

    public Integer getNumberOfEntitiesInDb() {
        return repositoryFactory.get(getEntityClass()).countAllByIsDeletedFalse();
    }
}
