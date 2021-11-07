package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.mappers.frontMappers.simple.FrontSimpleMappersFactory;
import ru.laptseu.libararyapp.models.dto.BookDto;
import ru.laptseu.libararyapp.models.dto.PublisherDto;
import ru.laptseu.libararyapp.models.dto.simpleDto.AuthorSimpleDto;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.models.entities.Publisher;
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
    public String createBookInLibrary(@Valid BookDto filledDto, BindingResult bindingResult, Model model) {
        List<String> errors = new ArrayList<>();
        if (filledDto.getId() != null) {
            errors.add("ID для нового пользователя не может существовать. ID=" + filledDto.getId());
            log.error("not saved as new with id " + filledDto.getId());//this URL only for new Entities
        }
        if (bindingResult.hasErrors()) {
            log.debug(bindingResult.getFieldErrors());
            bindingResult.getAllErrors().stream().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
        }
        if (!errors.isEmpty()) {
            model.addAttribute("entity", "книги");
            model.addAttribute("errors", errors);
            return "blocks/error_messages";
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
        if (filledDto.getPublisherSimpleDto().getId() == null) {
            filledDto.setPublisherSimpleDto(null);
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
        model.addAttribute("dto", dto);
        return "library/library_edit";
    }

    @PatchMapping("/id/{id}")
    public String updateBookInLibrary(@ModelAttribute("dto") BookDto dto, @PathVariable("id") Long id) {
        BookInLibrary origin = (BookInLibrary) serviceFactory.get(BookInLibrary.class).read(id);
        if (origin.getDescription() != dto.getDescription() || origin.getSectionNumber() != dto.getSectionNumber()) {
            origin.setDescription(dto.getDescription());
            origin.setSectionNumber(dto.getSectionNumber());
            serviceFactory.get(BookInLibrary.class).update(origin);
        }
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
