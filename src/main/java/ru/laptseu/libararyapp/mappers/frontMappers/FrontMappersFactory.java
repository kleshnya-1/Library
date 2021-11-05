package ru.laptseu.libararyapp.mappers.frontMappers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.EntityWithId;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class FrontMappersFactory {
    private static final Map<Class<? extends EntityWithId>, Class<? extends FrontMapper>> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put(Author.class, AuthorMapper.class);
        FACTORY_MAP.put(BookInLibrary.class, BookMapper.class);
        FACTORY_MAP.put(Publisher.class, PublisherMapper.class);
    }

    private final ApplicationContext applicationContext;

    public FrontMapper get(Class<? extends EntityWithId> clazz) {// TODO: 31.10.2021 parametrize
        return applicationContext.getBean(FACTORY_MAP.get(clazz));
    }
}
