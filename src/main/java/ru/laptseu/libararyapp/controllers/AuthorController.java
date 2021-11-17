package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.models.dto.AuthorDto;
import ru.laptseu.libararyapp.models.dto.BookDto;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.services.ServiceFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.validation.Valid;
import java.util.ArrayList;
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
        Pageable pageable = pageUtility.getPageable(page);
        List<AuthorDto> dtoList = frontMappersFactory.get(Author.class).map(serviceFactory.get(Author.class).readList(pageable));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("url", "authors");
        model.addAttribute("currentPageNum", page);
        model.addAttribute("isLastPage", pageUtility.getIsTheLastPage(Author.class, dtoList.size(), page));
        return "authors/author_first";
    }

    @PostMapping("/id/")
    public String createAuthor(@Valid AuthorDto filledDto, BindingResult bindingResult, Model model) {
        List<String> errors = new ArrayList<>();
        if (filledDto.getId() != null) {
            errors.add("ID для нового пользователя не может существовать. ID=" + filledDto.getId());
            log.error("not saved as new with id " + filledDto.getId());//this URL only for new Entities
        }
        if (bindingResult.hasErrors()) {
            log.debug(bindingResult.getFieldErrors());
            bindingResult.getAllErrors().stream().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
        }
        if (Objects.equals(filledDto.getFirstName(), "")) {
            filledDto.setFirstName(null);
        }
        if (Objects.equals(filledDto.getSecondName(), "")) {
            filledDto.setSecondName(null);
        }
        if (filledDto.getFirstName() == null && filledDto.getSecondName() == null &&
                filledDto.getBirthYear() == null && filledDto.getDeathYear() == null) {
            errors.add("Не заполненно ни одного поля. Такая запись не имеет смысла");
        }
        if (filledDto.getBirthYear() != null && filledDto.getBirthYear() > Calendar.getInstance().get(Calendar.YEAR)) {
            errors.add("Автор не может родиться в будущем (" + filledDto.getBirthYear() + " г.)");
        }
        if (filledDto.getDeathYear() != null && filledDto.getDeathYear() > Calendar.getInstance().get(Calendar.YEAR)) {
            errors.add("Дата смерти в будущем не может быть доподленно известна (" + filledDto.getDeathYear() + " г.)");
        }
        if (filledDto.getBirthYear() != null && filledDto.getDeathYear() != null && filledDto.getDeathYear() - filledDto.getBirthYear() < 0) {
            errors.add("Указанна дата рождения после даты смерти (" + filledDto.getBirthYear() + " " + filledDto.getDeathYear() + ")");
        }
        if (!errors.isEmpty()) {
            model.addAttribute("entity", "автора");
            model.addAttribute("errors", errors);
            return "blocks/error_messages";
        }
        Author author = (Author) frontMappersFactory.get(Author.class).map(filledDto);
        serviceFactory.get(Author.class).save(author);
        return startingUrl;
    }

    @GetMapping("/id/{id}")
    public String getAuthor(@PathVariable Long id, Model model) {
        AuthorDto dto = (AuthorDto) frontMappersFactory.get(Author.class).map(serviceFactory.get(Author.class).read(id));
        model.addAttribute("dto", dto);
        model.addAttribute("bookList", dto.getBookList());
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
    public String updateAuthor(@ModelAttribute("dto") AuthorDto dto, @PathVariable Long id, Model model) {
        List<String> errors = new ArrayList<>();
        Author authorFromDb = (Author) serviceFactory.get(Author.class).read(id);
        if (authorFromDb.getDeathYear() != null && dto.getDeathYear() == null) {
            errors.add("Год смерти не мог быть известным (" + dto.getDeathYear() + "г.) и стать неизвестным");
        }
        if (authorFromDb.getDeathYear() != null && authorFromDb.getDeathYear() != dto.getDeathYear()) {
            errors.add("Год смерти не мог мог измениться. (" + authorFromDb.getDeathYear() + " => " + dto.getDeathYear() + ") ");
        }
        if (dto.getDeathYear() != null && dto.getDeathYear() > Calendar.getInstance().get(Calendar.YEAR)) {
            errors.add("Дата смерти в будущем не может быть доподленно известна (" + dto.getDeathYear() + " г.)");
        }
        if (dto.getBirthYear() != null && dto.getDeathYear() != null && dto.getDeathYear() - dto.getBirthYear() < 0) {
            errors.add("Указанна дата рождения после даты смерти (" + dto.getBirthYear() + " " + dto.getDeathYear() + ")");
        }
        if (!errors.isEmpty()) {
            model.addAttribute("entity", "автора");
            model.addAttribute("errors", errors);
            return "blocks/error_messages";
        }
        if (dto.getDeathYear() == null && authorFromDb.getDeathYear() == null) {
            return startingUrl;//nothing to update
        }
        if (dto.getDeathYear() != null) {
            serviceFactory.get(Author.class).update(frontMappersFactory.get(Author.class).map((dto)));
        }
        return startingUrl;
    }

    @PostMapping("/id/{id}/remove")
    public String deleteAuthor(@PathVariable Long id) {
        serviceFactory.get(Author.class).delete(id);
        return startingUrl;
    }


}
