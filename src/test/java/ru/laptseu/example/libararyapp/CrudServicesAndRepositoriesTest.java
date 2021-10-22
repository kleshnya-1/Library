package ru.laptseu.example.libararyapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.laptseu.libararyapp.LibraryAppApplication;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.archive.BookArchiveRepository;
import ru.laptseu.libararyapp.repositories.library.BookLibraryRepository;
import ru.laptseu.libararyapp.repositories.library.PublisherRepository;
import ru.laptseu.libararyapp.services.BookArchiveService;
import ru.laptseu.libararyapp.services.BookLibraryService;
import ru.laptseu.libararyapp.services.PublisherService;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryAppApplication.class)
@DisplayName("Abstract Service Test")
class CrudServicesAndRepositoriesTest {
    @Autowired
    BookLibraryService bookLibraryService;
    @Autowired
    PublisherService publisherService;
    @Autowired
    BookArchiveRepository bookArchiveRepository;
    @Autowired
    BookLibraryRepository bookLibraryRepository;
    @Autowired
    BookArchiveService bookArchiveService;
    @Autowired
    PublisherRepository publisherRepository;

    BookInLibrary lb1;
    BookInLibrary lb2;
    BookArchived ab1;
    BookArchived ab2;
    Publisher p1;
    Publisher p2;

    @BeforeEach
    void before() throws Exception {
        lb1 = new BookInLibrary();
        lb1.setName("lb1 " + lb1.hashCode());
        lb2 = new BookInLibrary();
        lb2.setName("lb2 " + lb2.hashCode());
        ab1 = new BookArchived();
        ab1.setName("ab1 " + ab1.hashCode());
        ab2 = new BookArchived();
        ab2.setName("ab2 " + ab2.hashCode());
        p1 = new Publisher();
        p1.setName("p1 "+ Calendar.getInstance().getTime()+" " + p1.hashCode());
        p2 = new Publisher();
        p2.setName("p2 " + Calendar.getInstance().getTime()+" "+ p2.hashCode());
    }

    @AfterEach
    void after() throws Exception {
//        publisherRepository.deleteAll();
//        bookLibraryRepository.deleteAll();
    }


    @Test
    @DisplayName("Test Save Book")
    void testSaveBook() throws Exception {
        assertEquals(0, bookLibraryRepository.findAll().size());
        assertEquals(0, bookArchiveRepository.findAll().size());
        bookLibraryService.save(lb1);
        bookLibraryService.save(lb2);
        bookArchiveService.save(ab1);
        bookArchiveService.save(ab2);
        assertEquals(2, bookLibraryRepository.findAll().size());
        assertEquals(2, bookArchiveRepository.findAll().size());
    }

    @Test
    @DisplayName("Test Save Publisher")
    void testSavePublisher() throws Exception {
        assertEquals(0, publisherRepository.findAll().size());
        publisherService.save(p1);
        publisherService.save(p2);
        assertEquals(2, publisherRepository.findAll().size());
    }

    @Test
    @DisplayName("Test Read Book")
    void testReadBook() throws Exception {
        bookLibraryService.save(lb1);
        Long idB1 = lb1.getId();
        Long idB2 = bookLibraryService.save(lb2).getId();
        Long idBa1 = bookArchiveService.save(ab1).getId();
        Long idBa2 = bookArchiveService.save(ab2).getId();

        assertEquals(bookLibraryService.read(idB1).getName(), lb1.getName());
        assertEquals(bookLibraryService.read(idB2).getName(), lb2.getName());
        assertEquals(bookArchiveService.read(idBa1).getName(), ab1.getName());
        assertEquals(bookArchiveService.read(idBa2).getName(), ab2.getName());
    }

    @Test
    @DisplayName("Test Read Publisher")
    void testReadPublisher() throws Exception {
        Long idP1 = publisherService.save(p1).getId();
        Long idP2 = publisherService.save(p2).getId();
        assertEquals(publisherService.read(idP1).getName(), p1.getName());
        assertEquals(publisherService.read(idP2).getName(), p2.getName());
    }

    @Test
    @DisplayName("Test Update Book")
    void testUpdateBook() throws Exception {
        Long idLb1 = bookLibraryService.save(lb1).getId();
        Long idLb2 = bookLibraryService.save(lb2).getId();
        Long idAb1 = bookArchiveService.save(ab1).getId();
        Long idAb2 = bookArchiveService.save(ab2).getId();

        lb1.setName("newName lb1");
        lb2.setName("newName lb2");
        ab1.setName("newName ab1");
        ab2.setName("newName ab2");
        assertNotSame("newName lb1", bookLibraryService.read(idLb1).getName());
        assertNotSame("newName ab1", bookLibraryService.read(idAb1).getName());
        bookLibraryService.update(lb1);
        bookArchiveService.update(ab1);
        assertEquals("newName lb1", bookLibraryService.read(idLb1).getName());
        assertNotSame("newName lb2", bookLibraryService.read(idLb2).getName());
        assertEquals("newName ab1", bookArchiveService.read(idAb1).getName());
        assertNotSame("newName ab2", bookArchiveService.read(idAb2).getName());
    }

    @Test
    @DisplayName("Test Update Publisher")
    void testUpdatePublisher() throws Exception {
        Long idP1 = publisherService.save(p1).getId();
        Long idP2 = publisherService.save(p2).getId();
        assertNotSame("newName p1", publisherService.read(idP1).getName());
        assertNotSame("newName p2", publisherService.read(idP2).getName());
        p1.setName("newName p1");
        p2.setName("newName p2");
        publisherService.update(p1);
        publisherService.update(p2);
        assertEquals("newName p1", publisherService.read(idP1).getName());
        assertEquals("newName p2", publisherService.read(idP2).getName());
    }

    @Test
    @DisplayName("Test Delete Book")
    void testDeleteBook() throws Exception {
        Long idLb1 = bookLibraryService.save(lb1).getId();
        Long idLb2 = bookLibraryService.save(lb2).getId();
        Long idAb1 = bookArchiveService.save(ab1).getId();
        Long idAb2 = bookArchiveService.save(ab2).getId();
        assertEquals(bookLibraryService.read(idLb1).getName(), lb1.getName());
        assertEquals(bookLibraryService.read(idLb2).getName(), lb2.getName());
        assertEquals(bookArchiveService.read(idAb1).getName(), ab1.getName());
        assertEquals(bookArchiveService.read(idAb2).getName(), ab2.getName());

        bookLibraryService.delete(idLb1);
        bookLibraryService.delete(idLb2);
        bookArchiveService.delete(idAb1);
        bookArchiveService.delete(idAb2);
        assertNull(bookLibraryService.read(idLb1));
        assertNull(bookLibraryService.read(idLb2));
        assertNull(bookArchiveService.read(idAb1));
        assertNull(bookArchiveService.read(idAb2));
    }

    @Test
    @DisplayName("Test Delete Publisher")
    void testDeletePublisher() throws Exception {
        Long idP1 = publisherService.save(p1).getId();
        Long idP2 = publisherService.save(p2).getId();
        assertEquals(publisherService.read(idP1).getName(), p1.getName());
        assertEquals(publisherService.read(idP2).getName(), p2.getName());

        publisherService.delete(idP1);
        publisherService.delete(idP2);
        assertNull(publisherService.read(idP1));
        assertNull(publisherService.read(idP2));
    }
}