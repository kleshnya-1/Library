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
public class Logging extends EntityWithId {
    private String message;

    public Logging(String s) {
        message = s;
    }
}
