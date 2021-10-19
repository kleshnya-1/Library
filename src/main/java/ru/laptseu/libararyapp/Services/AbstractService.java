package ru.laptseu.libararyapp.Services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.laptseu.libararyapp.Entities.EntityWithLongId;
import ru.laptseu.libararyapp.Repositories.RepositoryFactory;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class AbstractService<T extends EntityWithLongId> {
    abstract Class getEntityClass();
    private final RepositoryFactory repositoryFactory;
    //private final PagingAndSortingRepository repository;// = repositoryFactory.get(getEntityClass());

    public T save(T entity) {
        repositoryFactory.get(getEntityClass()).save(entity);
        return entity;
    }

    public T read(Long id) {
        T entity = (T)  repositoryFactory.get(getEntityClass()).findById(id).orElse(null);//todo
        return entity;
    }

}
