package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;
import ru.laptseu.libararyapp.entities.dto.BookDto;
import ru.laptseu.libararyapp.entities.dto.PublisherDto;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.services.ServiceFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;
import ru.laptseu.libararyapp.utilities.TextTrimmingUtility;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class BookInLibraryController {
    private static final String startingUrl = "redirect:/library/1";
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;
    private final TextTrimmingUtility textTrimmingUtility;

    @GetMapping("/{page}")
    public String getBooksInLibrary(@PathVariable Integer page, Model model) {
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(serviceFactory.get(BookInLibrary.class).readList(page));
       dtoList.forEach(bookDto ->{
          bookDto.setDescription(textTrimmingUtility.trimToSize(bookDto.getDescription()));
       } );
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum(dtoList.size(), page));
        return "library/library_first";
    }

    @GetMapping("/by_author/{id}/{page}")
    public String getBooksInLibraryByAuthor(@PathVariable Long id, @PathVariable Integer page, Model model) {
        List<BookInLibrary> list;
        Author author = (Author) serviceFactory.get(Author.class).read(id);

            list =  ((Author) serviceFactory.get(Author.class).read(id)).getBookList();// TODO: 03.11.2021 aaaaaaaaaaaaaaAAAAAAAAAAAAAAAA

        String masterString = author.toString();
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(list);
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("authorId", id);
        model.addAttribute("master", " " + masterString + " ");
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum(dtoList.size(), page));
        return "library/library_by_querry";
    }

    @GetMapping("/by_publisher/{id}/{page}")
    public String getBooksInLibraryByPublisher(@PathVariable Long id, @PathVariable Integer page, Model model) {
        List<BookInLibrary> list;
        Publisher publisher = (Publisher) serviceFactory.get(Publisher.class).read(id);// TODO: 02.11.2021 aaaaaaaaaaaaaaaaaaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

            list = ((Publisher) serviceFactory.get(Publisher.class).read(id)).getBookList();

        String masterString = publisher.getName();
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(list);
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("publisherId", id);
        model.addAttribute("master", " " + masterString + " ");
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum(dtoList.size(), page));
        return "library/library_by_querry";
    }

    @PostMapping("/id/")
    public String createBookInLIbrary(@ModelAttribute @Valid BookDto filledDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors());
            return startingUrl;
        }
        if (filledDto.getId() != null) {
            log.error("not saved as new with id " + filledDto.getId());
            return startingUrl;
        }
        if (Objects.equals(filledDto.getDescription(), "")) {
            filledDto.setDescription(null);
        }
        if (Objects.equals(filledDto.getName(), "")) {
            filledDto.setName(null);
        }
        if (filledDto.isUnknownPublishingYear()) {
            filledDto.setYearOfPublishing(null);
        }
        if (filledDto.getPublisherDto().getId() == null) {
            filledDto.setPublisherDto(null);
        }
        BookInLibrary bookInLibrary = (BookInLibrary) frontMappersFactory.get(BookInLibrary.class).map((filledDto));
        List<Author> authorList = new ArrayList<>();
        for (String s : filledDto.getAuthorArrayForHtml()) {
            Author author = new Author();
            author.setId(Long.parseLong(s));
            authorList.add(author);
        }
        bookInLibrary.setAuthorList(authorList);
        serviceFactory.get(BookInLibrary.class).save(bookInLibrary);
        return startingUrl;
    }

    @GetMapping("/id/{id}")
    public String openBookInLibrary(@PathVariable Long id, Model model) {
        BookInLibrary bookInLibrary = (BookInLibrary) serviceFactory.get(BookInLibrary.class).read(id);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
        model.addAttribute("dto", dto);
        return "library/library_one";
    }

    @GetMapping("/id/library_new")
    public String getNewBookInLibraryPage(@ModelAttribute("emptyDto") BookDto emptyDto, Model model) {
        List<AuthorDto> authors = frontMappersFactory.get(Author.class).map(serviceFactory.get(Author.class).read());
        List<PublisherDto> publishers = serviceFactory.get(Publisher.class).read();
        model.addAttribute("authors", authors);

        model.addAttribute("publishers", publishers);
        return "library/library_new";
    }

    @GetMapping("/id/{id}/edit")
    public String editBookInLibrary(Model model, @PathVariable("id") Long id) {
        BookInLibrary bookInLibrary = (BookInLibrary) serviceFactory.get(BookInLibrary.class).read(id);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
        List<AuthorDto> authors = frontMappersFactory.get(Author.class).map(serviceFactory.get(Author.class).read());
        List<PublisherDto> publishers = frontMappersFactory.get(Publisher.class).map(serviceFactory.get(Publisher.class).read());
        model.addAttribute("dto", dto);
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);
        return "library/library_edit";
    }

    @PatchMapping("/id/{id}")
    public String updateBookInLibrary(@ModelAttribute("dto") BookDto dto) {
        BookInLibrary bookInLibrary = (BookInLibrary) frontMappersFactory.get(BookInLibrary.class).map((dto));
        List<Author> authorList;
        if (dto.getAuthorArrayForHtml().length > 0) {
            authorList = new ArrayList<>();
            bookInLibrary.setAuthorList(authorList);
            for (String s : dto.getAuthorArrayForHtml()) {
                Author author = new Author();
                author.setId(Long.parseLong(s));
                authorList.add(author);
            }
        } else {
            authorList = ((BookInLibrary) serviceFactory.get(BookInLibrary.class).read(bookInLibrary.getId())).getAuthorList();
        }
        bookInLibrary.setAuthorList(authorList);
        serviceFactory.get(BookInLibrary.class).update(bookInLibrary);
        return startingUrl;
    }

    @PostMapping("/id/{id}/remove")
    public String deleteBookInLIbrary(@PathVariable Long id) {
        serviceFactory.get(BookInLibrary.class).delete(id);
        return startingUrl;
    }

    @PostMapping("/id/{id}/to_archive")
    public String archiveBookInLibrary(@PathVariable Long id) {
        try {
            serviceFactory.get(BookInLibrary.class).toArchive(id);
            return "library/library_successful_archiving";
        } catch (OperationNotSupportedException e) {
            log.error(e);
        }
        return startingUrl;
    }
}
