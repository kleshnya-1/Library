package ru.laptseu.example.libararyapp;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.laptseu.libararyapp.LibraryAppApplication;
import ru.laptseu.libararyapp.entities.books.Book;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.library.BookLibraryRepository;
import ru.laptseu.libararyapp.services.BookLibraryService;

import java.util.Calendar;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryAppApplication.class)
class LibraryAppApplicationTests {
    @Autowired
    BookLibraryRepository bookLibraryRepository;
    @Autowired
    BookLibraryService bookLibraryService;
    @Test
    void contextLoads() {
        BookInLibrary b1 = new BookInLibrary();
        Book b11 = new BookInLibrary();
        Book b111 = new Book();

        Class c1 = b1.getClass();
        Class c11 = b11.getClass();
        Class c111 = b111.getClass();

        b1.setName(String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
        b1.setYearOfPublishing(1300);
        b1.setId(1000L);
      //  bookLibraryRepository.save(b1);
      Long l =  bookLibraryService.save(b1).getId();
       bookLibraryService.delete(l);
    }

}
