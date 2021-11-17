package ru.laptseu.libararyapp.utilities;

import lombok.Getter;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.entities.EntityWithId;

@Component
@Getter
public class HibernateUnproxyUtility<T extends EntityWithId> {

    public T unproxy(T proxied) {
        T entity = proxied;
        if (entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            entity = (T) ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }
}
