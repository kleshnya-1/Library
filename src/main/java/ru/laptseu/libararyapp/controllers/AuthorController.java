package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.EntityWithId;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;
import ru.laptseu.libararyapp.entities.dto.BookDto;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.services.ServiceFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Log4j2
@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private static final String startingUrl = "redirect:/authors/1";
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;

    @GetMapping("/{page}")
    public String getAuthors(@PathVariable Integer page, Model model) {
        List<AuthorDto> dtoList = frontMappersFactory.get(Author.class).map(serviceFactory.get(Author.class).readList(page));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum(dtoList.size(), page));
        return "authors/author_first";
    }

    @PostMapping("/id/")
    public String createAuthor(@ModelAttribute @Valid AuthorDto filledDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()||filledDto.getId() != null) {
            log.error(bindingResult.getFieldErrors());
            log.error("not saved as new with id " + filledDto.getId());//this URL only for new Entities
            return startingUrl;
        }
        if (Objects.equals(filledDto.getFirstName(), "")) {
            filledDto.setFirstName(null);
        }
        if (Objects.equals(filledDto.getSecondName(), "")) {
            filledDto.setSecondName(null);
        }
        if (filledDto.getFirstName() == null && filledDto.getSecondName() == null &&
                filledDto.getBirthYear() == null && filledDto.getDeathYear() == null) {
            return startingUrl;
        }
        if (filledDto.getBirthYear() != null && filledDto.getBirthYear() > Calendar.getInstance().get(Calendar.YEAR)) {
            return startingUrl;
        }
        if (filledDto.getBirthYear() != null && filledDto.getDeathYear() != null && filledDto.getDeathYear() - filledDto.getBirthYear() < 0) {
            return startingUrl;
        }
        if (filledDto.isUnknownBirthYear()) {
            filledDto.setBirthYear(null);
        }
        if (filledDto.isUnknownDeathYear()) {
            filledDto.setDeathYear(null);
        }
        Author author = (Author) frontMappersFactory.get(Author.class).map(filledDto);
        serviceFactory.get(Author.class).save(author);
        return startingUrl;
    }

    @GetMapping("/id/{id}")
    public String getAuthor(@PathVariable Long id, Model model) {
        Author author = (Author) serviceFactory.get(Author.class).read(id);
        String representing = author.toString();
        AuthorDto dto = (AuthorDto) frontMappersFactory.get(Author.class).map(author);
        model.addAttribute("dto", dto);
        model.addAttribute("representing", representing);
        return "authors/author_one";
    }

    @GetMapping("/id/authors_new")
    public String newAuthor(@ModelAttribute("emptyDto") AuthorDto emptyDto) {
        return "authors/author_new";
    }

    @GetMapping("/id/{id}/edit")
    public String editAuthor(Model model, @PathVariable("id") Long id) {
        Author author = (Author) serviceFactory.get(Author.class).read(id);
        AuthorDto dto = (AuthorDto) frontMappersFactory.get(Author.class).map(author);
        List<BookDto> dtoBooks = frontMappersFactory.get(BookInLibrary.class).map(author.getBookList());
        model.addAttribute("dto", dto);
        model.addAttribute("dtoList", dtoBooks);
        return "authors/author_edit";
    }

    @PatchMapping("/id/{id}")
    public String updateAuthor(@ModelAttribute("dto") AuthorDto dto) {
        serviceFactory.get(Author.class).update(frontMappersFactory.get(Author.class).map((dto)));
        return startingUrl;
    }

    @PostMapping("/id/{id}/remove")
    public String deleteAuthor(@PathVariable Long id) {
        serviceFactory.get(Author.class).delete(id);
        return startingUrl;
    }
}
