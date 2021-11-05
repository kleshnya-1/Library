package ru.laptseu.libararyapp.mappers.frontMappers.simple;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMapper;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.EntityWithId;
import ru.laptseu.libararyapp.models.entities.Publisher;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class FrontSimpleMappersFactory {
    private static final Map<Class<? extends EntityWithId>, Class<? extends FrontMapper>> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put(Author.class, AuthorSimpleMapper.class);

        FACTORY_MAP.put(Publisher.class, PublisherSimpleMapper.class);
    }

    private final ApplicationContext applicationContext;

    public FrontMapper get(Class<? extends EntityWithId> clazz) {// TODO: 31.10.2021 parametrize
        return applicationContext.getBean(FACTORY_MAP.get(clazz));
    }
}
