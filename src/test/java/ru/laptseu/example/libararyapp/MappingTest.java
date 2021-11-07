package ru.laptseu.example.libararyapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.LibraryAppApplication;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.models.dto.AuthorDto;
import ru.laptseu.libararyapp.models.dto.BookDto;
import ru.laptseu.libararyapp.models.dto.EntityDto;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.library.BookLibraryRepository;
import ru.laptseu.libararyapp.services.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryAppApplication.class)
@DisplayName("Archive Service Test")
@Transactional(value = "libraryTransactionManager")
class MappingTest {
    @Autowired
    BookLibraryService bookLibraryService;
    @Autowired
    BookLibraryRepository bookLibraryRepository;
    @Autowired
    BookArchiveService bookArchiveService;
    @Autowired
    ServiceFactory serviceFactory;
    @Autowired
    AuthorService authorService;
    @Autowired
    BookLibraryService libraryService;
    @Autowired
    BookArchiveService archiveService;
    @Autowired
    PublisherService publisherService;
    @Autowired
    FrontMappersFactory frontMappersFactory;

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
    }

    @Test
    @DisplayName("Test Mapping")
    void testMapping() throws Exception {
        lb1.setPublisher(p1);
        lb1.setAuthorList(listOfThreeAuthors);
        lb2.setPublisher(p2);
        lb2.setAuthorList(listOfOneAuthor);
        bookLibId1 = bookLibraryService.save(lb1).getId();
        bookLibId2 = bookLibraryService.save(lb2).getId();
        bookArchId1 = bookArchiveService.save(ab1).getId();
        bookArchId2 = bookArchiveService.save(ab2).getId();

        BookInLibrary bookFromLib1 = libraryService.read(bookLibId1);
        BookInLibrary bookFromLib2 = libraryService.read(bookLibId2);
        BookArchived bookFromArchive1 = archiveService.read(bookArchId1);
        BookArchived bookFromArchive2 = archiveService.read(bookArchId2);
        assertEquals(3, bookFromLib1.getAuthorList().size());
        assertEquals(1, bookFromLib2.getAuthorList().size());

        Long archivingId1 = libraryService.toArchive(bookFromLib1.getId()).getId();
        Long archivingId2 = libraryService.toArchive(bookFromLib2.getId()).getId();
        BookArchived bookWeArchived1 = archiveService.read(archivingId1);
        BookArchived bookWeArchived2 = archiveService.read(archivingId2);
        BookInLibrary bookWeUnArchived1 = archiveService.fromArchive(bookWeArchived1.getId());
        BookInLibrary bookWeUnArchived2 = archiveService.fromArchive(bookWeArchived2.getId());
        assertEquals(p1.getName(), bookWeUnArchived1.getPublisher().getName());
        assertEquals(p2.getName(), bookWeUnArchived2.getPublisher().getName());
        assertEquals(3, bookWeUnArchived1.getAuthorList().size());
        assertEquals(1, bookWeUnArchived2.getAuthorList().size());
    }

    @Test
    @DisplayName("Test Mapping Dto")
    void testMappingDto() throws Exception {// TODO: 31.10.2021 finish
        Author author1 = authorService.read(a1SavedId);
        Author author2 = authorService.read(a2SavedId);
        AuthorDto authorDto1 = (AuthorDto) frontMappersFactory.get(Author.class).map(authorService.read(a1SavedId));
        AuthorDto authorDto2 = (AuthorDto) frontMappersFactory.get(Author.class).map(authorService.read(a2SavedId));


        List<EntityDto> aList1 = frontMappersFactory.get(Author.class).map(authorService.readList(1));
        List<EntityDto> aList2 = frontMappersFactory.get(Author.class).map(authorService.readList(2));
        List<EntityDto> aList3 = frontMappersFactory.get(Author.class).map(authorService.readList(3));
        BookDto bookDto = new BookDto();
        List list = new ArrayList();
        list.add(authorDto1);
        list.add(authorDto2);
        bookDto.setAuthorList(list);
    }
}