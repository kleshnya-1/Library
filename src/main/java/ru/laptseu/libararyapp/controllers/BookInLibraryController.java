package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class BookInLibraryController {
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;

    @GetMapping("/{page}")
    public String openPage(@PathVariable Integer page, Model model) {
        List<BookDto> dtoList = serviceFactory.get(BookInLibrary.class).readDtoList(page);
        model.addAttribute("dtoList", dtoList);
        int lastPageNum = dtoList.size()/pageUtility.getNumberOfEntitiesPerPage() + 1;
        if (page == 1) {
            model.addAttribute("exPageNum", page);
        } else {
            model.addAttribute("exPageNum", page - 1);
        }
        if (page == lastPageNum) {
            model.addAttribute("nextPageNum", page);
        } else {
            model.addAttribute("nextPageNum", page + 1);
        }// TODO: 28.10.2021 th:text="@{{id}(id=${a.getFirstName()})} ref in html
        return "library/library_first";
    }

    @GetMapping("/by_author/{id}/{page}")
    public String booksByAuthor(@PathVariable Long id, @PathVariable Integer page, Model model) {
        List<BookInLibrary> list = new ArrayList<>() ;
        AuthorDto authorDto = (AuthorDto) serviceFactory.get(Author.class).readDto(id);
        try {
            list = serviceFactory.get(BookInLibrary.class).readBooksByAuthor(id);
        } catch (OperationNotSupportedException e) {
            log.error(e);
        }
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(list);
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("authorId", id);
        model.addAttribute("author", authorDto);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum( dtoList.size(), page));
        return "library/library_by_author";
    }

    @PostMapping("/id/")
    public String submit(@ModelAttribute @Valid BookDto filledDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/library/1";
        }
        if (filledDto.getId() != null) {
            return "redirect:/library/1";
        }
        BookInLibrary bookInLibrary = (BookInLibrary) serviceFactory.get(BookInLibrary.class).fromDto(filledDto);
        serviceFactory.get(BookInLibrary.class).save(bookInLibrary);
        return "redirect:/library/1";
    }

    @GetMapping("/id/{id}")
    public String openPersonalPage(@PathVariable Long id, Model model) {
        BookInLibrary bookInLibrary = (BookInLibrary) serviceFactory.get(BookInLibrary.class).read(id);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
      //  List<AuthorDto> dtoAuthors = frontMappersFactory.get(Author.class).map(bookInLibrary.getAuthorList());
        model.addAttribute("dto", dto);
      //  model.addAttribute("dtoList", dtoAuthors);
        return "library/library_one";
    }

    @GetMapping("/id/library_new")
    public String newAccount(@ModelAttribute("emptyDto") BookDto emptyDto, Model model) {
        //List<AuthorDto> authors = serviceFactory.get(Author.class).re
        //model.addAttribute("authors", authors);
        return "library/library_new";
    }

    @GetMapping("/id/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        BookInLibrary bookInLibrary = (BookInLibrary) serviceFactory.get(BookInLibrary.class).read(id);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
        List<AuthorDto> authors = frontMappersFactory.get(Author.class).map(bookInLibrary.getAuthorList());
        model.addAttribute("dto", dto);
        model.addAttribute("dtoList", authors);
        return "library/library_edit";
    }



    @PatchMapping("/id/{id}")
    public String update(@ModelAttribute("dto") BookDto dto) {
        serviceFactory.get(BookInLibrary.class).update(serviceFactory.get(BookInLibrary.class).fromDto(dto));
        return "redirect:/library/1";
    }

    @PostMapping("/id/{id}/remove")
    public String delete(@PathVariable Long id) {
        serviceFactory.get(BookInLibrary.class).delete(id);
        return "redirect:/library/1";
    }

    @PostMapping("/id/{id}/to_archive")
    public String toArchive(@PathVariable Long id) {
        try {
            serviceFactory.get(BookInLibrary.class).toArchive(id);
            return "library/library_successful_archiving";
        } catch (OperationNotSupportedException e) {
            log.error(e);
        }
        return "redirect:/library/1";
    }
}
