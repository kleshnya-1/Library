package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.LoggingEntity;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

@Getter
@Service
public class LoggingService extends AbstractService<LoggingEntity> {
    Class entityClass = LoggingEntity.class;

    public LoggingService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
    }
}
