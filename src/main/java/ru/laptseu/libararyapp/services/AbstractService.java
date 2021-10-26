package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import ru.laptseu.libararyapp.entities.Entity;
import ru.laptseu.libararyapp.entities.EntityWithLongId;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.entities.dto.EntityDto;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public abstract class AbstractService<T extends EntityWithLongId> {
    private final RepositoryFactory repositoryFactory;
    private final PageUtility pageUtility;
    private final FrontMappersFactory frontMappersFactory;

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

    public EntityDto readDto(Long id) {
        return toDto(read(id));
    }

    public List readDtoList(Integer page) {
        Pageable pageable = pageUtility.getPageable(page);
        List<Entity> entityList = repositoryFactory.get(getEntityClass()).findPageable(pageable);
        List dtoList = entityList.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
        return dtoList;
    }


    public EntityDto toDto(Entity entity) {
        return frontMappersFactory.get(getEntityClass()).map(entity);
    }

    public T fromDto(EntityDto entityDto) {
        FrontMapper ma = frontMappersFactory.get(getEntityClass());
        return (T) frontMappersFactory.get(getEntityClass()).map(entityDto);
    }

//    public List<EntityDto> toDto(List<Entity> entity) {
//        return frontMappersFactory.get(getEntityClass()).map(entity);
//    }
//
//    public List<T> fromDto(List<EntityDto> entityDto) {
//        FrontMapper ma = frontMappersFactory.get(getEntityClass());
//        return (T) frontMappersFactory.get(getEntityClass()).map(entityDto);
//    }
}
