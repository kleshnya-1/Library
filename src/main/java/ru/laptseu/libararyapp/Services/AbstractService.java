package ru.laptseu.libararyapp.Services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.laptseu.libararyapp.Entities.EntityWithLongId;
import ru.laptseu.libararyapp.Repositories.RepositoryFactory;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class AbstractService<T extends EntityWithLongId> {
 abstract   Class getEntity() ;
 @Autowired
    private final RepositoryFactory repositoryFactory;
    PagingAndSortingRepository repository = repositoryFactory.get(getEntity().getClass());

    public T save(T entity) {
        repository.save(entity);
        return entity;
    }

    public T read(Long id) {
        T entity = (T) repository.findById(id).orElse(null);//todo
        return entity;
    }

}
