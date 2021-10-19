package ru.laptseu.libararyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.laptseu.libararyapp.Entities.Author;
import ru.laptseu.libararyapp.Entities.Books.BookArchive;

@SpringBootApplication
//@EnableJpaRepositories
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
