//package ru.laptseu.example.libararyapp;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.laptseu.libararyapp.LibraryAppApplication;
//import ru.laptseu.libararyapp.entities.Publisher;
//import ru.laptseu.libararyapp.entities.books.ArchivedBook;
//import ru.laptseu.libararyapp.entities.books.Book;
//import ru.laptseu.libararyapp.entities.books.LibraryBook;
//import ru.laptseu.libararyapp.repositories.archive.BookArchiveRepository;
//import ru.laptseu.libararyapp.repositories.library.BookLibraryRepository;
//import ru.laptseu.libararyapp.repositories.library.PublisherRepository;
//import ru.laptseu.libararyapp.services.BookService;
//import ru.laptseu.libararyapp.services.PublisherService;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = LibraryAppApplication.class)
//@DisplayName("Abstract Service Test")
//class CrudServicesAndRepositoriesTest {
//    @Autowired
//    BookService bookService;
//    @Autowired
//    PublisherService publisherService;
//    @Autowired
//    BookArchiveRepository bookArchiveRepository;
//    @Autowired
//    BookLibraryRepository bookLibraryRepository;
//    @Autowired
//    PublisherRepository publisherRepository;
//
//    Book lb1;
//    LibraryBook lb2;
//    Book ab1;
//    ArchivedBook ab2;
//    Publisher p1;
//    Publisher p2;
//
//    @BeforeEach
//    void before() throws Exception {
//        lb1 = new LibraryBook();
//        lb2 = new LibraryBook();
//        ab1 = new ArchivedBook();
//        ab2 = new ArchivedBook();
//        p1 = new Publisher();
//        p2 = new Publisher();
//    }
//
//    @AfterEach
//    void after() throws Exception {
//    }
//
//
//    @Test
//    @DisplayName("Test Save")
//    void testSaveBook() throws Exception {
//        assertEquals(0, bookLibraryRepository.findAll().size());
//        assertEquals(0, bookArchiveRepository.findAll().size());
//        bookService.save(lb1);
//        bookService.save(lb2);
//        bookService.save(ab1);
//        bookService.save(ab2);
//        assertEquals(2, bookLibraryRepository.findAll().size());
//        assertEquals(2, bookArchiveRepository.findAll().size());
//    }
//
//    @Test
//    @DisplayName("Test Save")
//    void testSavePublisher() throws Exception {
//        assertEquals(0, publisherRepository.findAll().size());
//        publisherService.save(p1);
//        publisherService.save(p2);
//        assertEquals(2, publisherRepository.findAll().size());
//    }
//
//    @Test
//    @DisplayName("Test Read")
//    void testReadBook() throws Exception {
//        Long idB1 = bookService.save(lb1).getId();
//        Long idB2 = bookService.save(lb2).getId();
//        Long idBa1 = bookService.save(ab1).getId();
//        Long idBa2 = bookService.save(ab2).getId();
//
//        assertSame(bookService.read(idB1), lb1);
//        assertSame(bookService.read(idB2), lb2);
//        assertSame(bookService.read(idBa1), ab1);
//        assertSame(bookService.read(idBa2), ab2);
//    }
//
//    @Test
//    @DisplayName("Test Read")
//    void testReadPublisher() throws Exception {
//        Long idP1 = publisherService.save(p1).getId();
//        Long idP2 = publisherService.save(p2).getId();
//        assertSame(publisherService.read(idP1), p1);
//        assertSame(publisherService.read(idP2), p2);
//    }
//
//    @Test
//    @DisplayName("Test Update")
//    void testUpdateBook() throws Exception {
//        Long idLb1 = bookService.save(lb1).getId();
//        Long idLb2 = bookService.save(lb2).getId();
//        Long idAb1 = bookService.save(ab1).getId();
//        Long idAb2 = bookService.save(ab2).getId();
//
//        lb1.setName("newName lb1");
//        lb2.setName("newName lb2");
//        ab1.setName("newName ab1");
//        ab2.setName("newName ab2");
//        assertNotSame("newName lb1", bookService.read(idLb1).getName());
//        assertNotSame("newName ab1", bookService.read(idAb1).getName());
//        bookService.update(lb1);
//        bookService.update(ab1);
//        assertSame(bookService.read(idLb1).getName(), "newName lb1");
//        assertNotSame("newName lb2", bookService.read(idLb2).getName());
//        assertSame(bookService.read(idAb1).getName(), "newName ab1");
//        assertNotSame("newName ab2", bookService.read(idAb2).getName());
//    }
//
//    @Test
//    @DisplayName("Test Update")
//    void testUpdatePublisher() throws Exception {
//        Long idP1 = publisherService.save(p1).getId();
//        Long idP2 = publisherService.save(p2).getId();
//        assertNotSame(publisherService.read(idP1).getName(), "newName p1");
//        assertNotSame(publisherService.read(idP2).getName(), "newName p2");
//        p1.setName("newName p1");
//        p2.setName("newName p2");
//        assertSame(publisherService.read(idP1).getName(), "newName p1");
//        assertSame(publisherService.read(idP2).getName(), "newName p2");
//    }
//
//    @Test
//    @DisplayName("Test Delete")
//    void testDeleteBook() throws Exception {
//        Long idLb1 = bookService.save(lb1).getId();
//        Long idLb2 = bookService.save(lb2).getId();
//        Long idAb1 = bookService.save(ab1).getId();
//        Long idAb2 = bookService.save(ab2).getId();
//        assertSame(bookService.read(idLb1), lb1);
//        assertSame(bookService.read(idLb2), lb2);
//        assertSame(bookService.read(idAb1), ab1);
//        assertSame(bookService.read(idAb2), ab2);
//
//        bookService.delete(idLb1);
//        bookService.delete(idLb2);
//        bookService.delete(idAb1);
//        bookService.delete(idAb2);
//        assertNull(bookService.read(idLb1));
//        assertNull(bookService.read(idLb2));
//        assertNull(bookService.read(idAb1));
//        assertNull(bookService.read(idAb2));
//    }
//
//    @Test
//    @DisplayName("Test Delete")
//    void testDeletePublisher() throws Exception {
//        Long idP1 = publisherService.save(p1).getId();
//        Long idP2 = publisherService.save(p2).getId();
//        assertSame(publisherService.read(idP1), p1);
//        assertSame(publisherService.read(idP2), p2);
//
//        publisherService.delete(idP1);
//        publisherService.delete(idP2);
//        assertNull(publisherService.read(idP1));
//        assertNull(publisherService.read(idP2));
//    }
//}