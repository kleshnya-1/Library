package ru.laptseu.libararyapp.entities;


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
public class LoggingEntity extends EntityWithLongId {
    private String message;

    public LoggingEntity(String s) {
        message = s;
    }
}
