package ru.laptseu.example.libararyapp;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.laptseu.libararyapp.LibraryAppApplication;
import ru.laptseu.libararyapp.entities.books.Book;
import ru.laptseu.libararyapp.entities.books.LibraryBook;
import ru.laptseu.libararyapp.services.BookService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryAppApplication.class)
class LibraryAppApplicationTests {
    @Autowired
    BookService bookService;
    @Test
    void contextLoads() {
        LibraryBook b1 = new LibraryBook();
       bookService.save(b1);


    }

}
