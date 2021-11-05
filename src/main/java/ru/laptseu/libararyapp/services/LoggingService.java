package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.models.entities.LoggingEntity;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

@Getter
@Service
public class LoggingService extends AbstractService<LoggingEntity> {

    public LoggingService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
    }
}
