package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;
import ru.laptseu.libararyapp.entities.dto.BookDto;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.services.ServiceFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {// TODO: 27.10.2021 нормальные названия методов
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;

    @GetMapping("/{page}")
    public String openPage(@PathVariable Integer page, Model model) {
        List<AuthorDto> dtoList = serviceFactory.get(Author.class).readDtoList(page);
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum( dtoList.size(), page));
        return "authors/author_first";
    }

    @PostMapping("/id/")
    public String submit(@ModelAttribute @Valid AuthorDto filledDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/authors/1";
        }
        if (filledDto.getId() != null) {//this URL only for new Entities
            return "redirect:/authors/1";
        }
        if (filledDto.getFirstName() == "" && filledDto.getSecondName() == "" &&
                filledDto.getBirthYear() == null && filledDto.getDeathYear() == null) {
            return "redirect:/authors/1";
        }
        if (filledDto.getBirthYear() != null && filledDto.getBirthYear() > Calendar.getInstance().get(Calendar.YEAR)) {
            return "redirect:/authors/1";
        }
        if (filledDto.getBirthYear() != null && filledDto.getDeathYear() != null) {
            if (filledDto.getDeathYear() - filledDto.getBirthYear() < 0) {
                return "redirect:/authors/1";
            }
        }
        Author author = (Author) serviceFactory.get(Author.class).fromDto(filledDto);
        serviceFactory.get(Author.class).save(author);
        return "redirect:/authors/1";
    }

    @GetMapping("/id/{id}")
    public String openPersonalPage(@PathVariable Long id, Model model) {
        Author author = (Author) serviceFactory.get(Author.class).read(id);
        AuthorDto dto = (AuthorDto) frontMappersFactory.get(Author.class).map(author);
        List<BookDto> dtoBooks = frontMappersFactory.get(BookInLibrary.class).map(author.getBookList());
        model.addAttribute("dto", dto);
        model.addAttribute("dtoList", dtoBooks);
        return "authors/author_one";
    }

    @GetMapping("/id/authors_new")
    public String newAccount(@ModelAttribute("emptyDto") AuthorDto emptyDto) {

        return "authors/author_new";
    }

    @GetMapping("/id/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        Author author = (Author) serviceFactory.get(Author.class).read(id);
        AuthorDto dto = (AuthorDto) frontMappersFactory.get(Author.class).map(author);
        List<BookDto> dtoBooks = frontMappersFactory.get(BookInLibrary.class).map(author.getBookList());
        model.addAttribute("dto", dto);
        model.addAttribute("dtoList", dtoBooks);
        return "authors/author_edit";
    }

    @PatchMapping("/id/{id}")
    public String update(@ModelAttribute("dto") AuthorDto dto) {
        serviceFactory.get(Author.class).update(serviceFactory.get(Author.class).fromDto(dto));
        return "redirect:/authors/1";
    }

    @PostMapping("/id/{id}/remove")
    public String delete(@PathVariable Long id) {
        serviceFactory.get(Author.class).delete(id);
        return "redirect:/authors/1";
    }
}
