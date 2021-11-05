package ru.laptseu.example.libararyapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.laptseu.libararyapp.LibraryAppApplication;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
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
    @Autowired
    AuthorService authorService;

    AbstractService libraryService;
    AbstractService archiveService;

    BookInLibrary lb1;
    BookInLibrary lb2;
    BookArchived ab1;
    BookArchived ab2;
    Publisher p1;
    Publisher p2;
    Author a1;
    Author a2;
    Author a3;
    Author a4;
    List<Author> listOfThreeAuthors;
    List<Author> listOfOneAuthor;


    Long bookLibId1;
    Long bookLibId2;
    Long bookArchId1;
    Long bookArchId2;
    Long a1SavedId;
    Long a2SavedId;
    Long a3SavedId;
    Long a4SavedId;

    @BeforeEach
    void before() throws Exception {
        a1 = new Author();
        a2 = new Author();
        a3 = new Author();
        a4 = new Author();
        a1.setFirstName("a1 " + Calendar.getInstance().getTime());
        a2.setFirstName("a2 " + Calendar.getInstance().getTime());
        a3.setFirstName("a3 " + Calendar.getInstance().getTime());
        a4.setFirstName("a4 " + Calendar.getInstance().getTime());
        a1SavedId = authorService.save(a1).getId();
        a2SavedId = authorService.save(a2).getId();
        a3SavedId = authorService.save(a3).getId();
        a4SavedId = authorService.save(a4).getId();

        listOfThreeAuthors = new ArrayList<>();
        listOfThreeAuthors.add(a1);
        listOfThreeAuthors.add(a2);
        listOfThreeAuthors.add(a3);
        listOfOneAuthor = new ArrayList<>();
        listOfOneAuthor.add(a4);

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
        publisherService.save(p1);
        publisherService.save(p2);

        lb1.setAuthorList(listOfThreeAuthors);
        lb1.setPublisher(p1);
        lb2.setAuthorList(listOfOneAuthor);
        lb2.setPublisher(p2);

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
        BookInLibrary bookFromLib1 = (BookInLibrary) libraryService.read(bookLibId1);
        BookInLibrary bookFromLib2 = (BookInLibrary) libraryService.read(bookLibId2);
        BookArchived bookFromArchive1 = (BookArchived) archiveService.read(bookArchId1);
        BookArchived bookFromArchive2 = (BookArchived) archiveService.read(bookArchId2);

        int sizeL = bookLibraryRepository.findAll().size();
        int sizeA = bookArchiveRepository.findAll().size();
        Long idOfArchivingLb1 = libraryService.toArchive(bookFromLib1).getId();
        Long idOfArchivingLb2 = libraryService.toArchive(bookFromLib2).getId();

        assertEquals(2 + sizeA, bookArchiveRepository.findAll().size());

        BookInLibrary bookInLibraryFromArchive1 = archiveService.fromArchive(bookFromArchive1);
        BookInLibrary bookInLibraryFromArchive2 = archiveService.fromArchive(bookFromArchive2);
        assertEquals(bookFromArchive1.getName(), bookInLibraryFromArchive1.getName());

        assertEquals(sizeL, bookLibraryRepository.findAll().size());
        assertEquals(sizeA, bookArchiveRepository.findAll().size());

        BookInLibrary bookInLibraryFromArchiveFromLibrary1 = archiveService.fromArchive(idOfArchivingLb1);
        BookInLibrary bookInLibraryFromArchiveFromLibrary2 = archiveService.fromArchive(idOfArchivingLb2);

        assertEquals(bookFromLib1.getAuthorList().get(1).getFirstName(), bookInLibraryFromArchiveFromLibrary1.getAuthorList().get(1).getFirstName());
        assertEquals(bookFromLib2.getAuthorList().get(0).getFirstName(), bookInLibraryFromArchiveFromLibrary2.getAuthorList().get(0).getFirstName());
    }
}