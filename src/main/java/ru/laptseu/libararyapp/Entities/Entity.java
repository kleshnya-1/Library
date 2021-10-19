package ru.laptseu.libararyapp.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class Entity {
    private boolean isDeleted;
}
