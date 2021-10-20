package ru.laptseu.libararyapp.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

@Getter
@Setter
@Entity//really?
//@MappedSuperclass
public  abstract class EntityWithLongId extends ru.laptseu.libararyapp.entities.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
