package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.mappers.frontMappers.simple.FrontSimpleMappersFactory;
import ru.laptseu.libararyapp.models.dto.AuthorSimpleDto;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.models.dto.AuthorDto;
import ru.laptseu.libararyapp.models.dto.BookDto;
import ru.laptseu.libararyapp.models.dto.PublisherDto;
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
    private final FrontSimpleMappersFactory frontSimpleMappersFactory;
    private final PageUtility pageUtility;
    private final TextTrimmingUtility textTrimmingUtility;// TODO: 04.11.2021 to static

    @GetMapping("/{page}")
    public String getBooksInLibrary(@PathVariable Integer page, Model model) {
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(serviceFactory.get(BookInLibrary.class).readList(page));
        dtoList.forEach(bookDto -> bookDto.setDescription(textTrimmingUtility.trimToSize(bookDto.getDescription())));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("url", "library");
        model.addAttribute("currentPageNum", page);
        model.addAttribute("isLastPage", pageUtility.getIsFullPage(dtoList.size(), page));
        return "library/library_first";
    }



    @PostMapping("/id/")
    public String createBookInLibrary(@ModelAttribute @Valid BookDto filledDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || filledDto.getId() != null) {
            log.error(bindingResult.getFieldErrors());
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
        List<AuthorSimpleDto> authors = frontSimpleMappersFactory.get(Author.class).map(serviceFactory.get(Author.class).read());
        List<PublisherDto> publishers = frontSimpleMappersFactory.get(Publisher.class).map(serviceFactory.get(Publisher.class).read());
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
        if (dto.getPublisherDto().getId() == null) {
            dto.setPublisherDto(null);
        }
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
    @GetMapping("/by_author/{id}/{page}")
    public String getBooksInLibraryByAuthor(@PathVariable Long id, @PathVariable Integer page, Model model) {
        Author author = (Author) serviceFactory.get(Author.class).read(id);
        String masterString = author.toString();
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(author.getBookList());
        dtoList.forEach(bookDto -> bookDto.setDescription(textTrimmingUtility.trimToSize(bookDto.getDescription())));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("authorId", id);
        model.addAttribute("master", " " + masterString + " ");
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum(dtoList.size(), page));
        model.addAttribute("path", "/by_author/");
        return "library/library_by_querry";
    }

    @GetMapping("/by_publisher/{id}/{page}")
    public String getBooksInLibraryByPublisher(@PathVariable Long id, @PathVariable Integer page, Model model) {
        Publisher publisher = (Publisher) serviceFactory.get(Publisher.class).read(id);
        String masterString = publisher.getName();
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(publisher.getBookList());
        dtoList.forEach(bookDto -> bookDto.setDescription(textTrimmingUtility.trimToSize(bookDto.getDescription())));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("publisherId", id);
        model.addAttribute("master", " " + masterString + " ");
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum(dtoList.size(), page));
        model.addAttribute("path", "/by_publisher/");
        return "library/library_by_querry";
    }
}
