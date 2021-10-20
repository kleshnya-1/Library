package ru.laptseu.libararyapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class Entity {
    private boolean isDeleted;
}
