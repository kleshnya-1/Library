package ru.laptseu.example.libararyapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.laptseu.libararyapp.LibraryAppApplication;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.repositories.archive.BookArchiveRepository;
import ru.laptseu.libararyapp.repositories.library.BookLibraryRepository;
import ru.laptseu.libararyapp.repositories.library.PublisherRepository;
import ru.laptseu.libararyapp.services.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryAppApplication.class)
@DisplayName("Archive Service Test")
class ArchivingTest {
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
    @Autowired
    ServiceFactory serviceFactory;

    AbstractService libraryService;
    AbstractService archiveService;

    BookInLibrary lb1;
    BookInLibrary lb2;
    BookArchived ab1;
    BookArchived ab2;
    Publisher p1;
    Publisher p2;


    Long bookLibId1;
    Long bookLibId2;
    Long bookArchId1;
    Long bookArchId2;


    @BeforeEach
    void before() throws Exception {
        lb1 = new BookInLibrary();
        lb1.setName("created as lb1 " + Calendar.getInstance().getTime());
        lb2 = new BookInLibrary();
        lb2.setName("created as lb2 " + Calendar.getInstance().getTime());
        ab1 = new BookArchived();
        ab1.setName("created as ab1 " + Calendar.getInstance().getTime());
        ab2 = new BookArchived();
        ab2.setName("created as ab2 " + Calendar.getInstance().getTime());
        p1 = new Publisher();
        p1.setName("p1 " + Calendar.getInstance().getTime() + " " + p1.hashCode());
        p2 = new Publisher();
        p2.setName("p2 " + Calendar.getInstance().getTime() + " " + p2.hashCode());




        bookLibId1 = bookLibraryService.save(lb1).getId();
        bookLibId2 = bookLibraryService.save(lb2).getId();
        bookArchId1 = bookArchiveService.save(ab1).getId();
        bookArchId2 = bookArchiveService.save(ab2).getId();

        libraryService = serviceFactory.get(BookInLibrary.class);
        archiveService = serviceFactory.get(BookArchived.class);
    }



    @Test
    @DisplayName("Test Archive and UnArchive Books")
    void testToArchive() throws Exception {
        assertEquals(2, bookLibraryRepository.findAll().size());
        assertEquals(2, bookArchiveRepository.findAll().size());

        BookInLibrary bookFromLib1 = (BookInLibrary) libraryService.read(bookLibId1);
        BookInLibrary bookFromLib2 = (BookInLibrary) libraryService.read(bookLibId2);
        BookArchived bookFromArchive1 = (BookArchived) archiveService.read(bookArchId1);
        BookArchived bookFromArchive2 = (BookArchived) archiveService.read(bookArchId2);

        libraryService.toArchive(bookFromLib1);
        libraryService.toArchive(bookFromLib2);
        assertEquals(0, bookLibraryRepository.findAll().size());
        assertEquals(4, bookArchiveRepository.findAll().size());

        archiveService.fromArchive(bookFromArchive1);
        archiveService.fromArchive(bookFromArchive2);
        assertEquals(2, bookLibraryRepository.findAll().size());
        assertEquals(2, bookArchiveRepository.findAll().size());
    }


}