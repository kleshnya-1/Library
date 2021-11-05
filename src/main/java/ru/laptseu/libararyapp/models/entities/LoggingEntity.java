package ru.laptseu.libararyapp.models.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class LoggingEntity extends EntityWithId {
    private String message;

    public LoggingEntity(String s) {
        message = s;
    }
}
