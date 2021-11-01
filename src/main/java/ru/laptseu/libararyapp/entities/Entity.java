package ru.laptseu.libararyapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Entity {
    private boolean isDeleted;
}


