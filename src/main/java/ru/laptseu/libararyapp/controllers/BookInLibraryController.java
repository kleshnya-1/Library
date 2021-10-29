package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.EntityWithLongId;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;
import ru.laptseu.libararyapp.entities.dto.BookDto;
import ru.laptseu.libararyapp.entities.dto.PublisherDto;
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
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(serviceFactory.get(BookInLibrary.class).readList(page));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum( dtoList.size(), page));
    // TODO: 28.10.2021 th:text="@{{id}(id=${a.getFirstName()})} ref in html
        return "library/library_first";
    }

    @GetMapping("/by_author/{id}/{page}")
    public String booksByAuthor(@PathVariable Long id, @PathVariable Integer page, Model model) {
        List<BookInLibrary> list ;
        AuthorDto authorDto = (AuthorDto) frontMappersFactory.get(Author.class).map(serviceFactory.get(Author.class).read(id));
        try {
            list = serviceFactory.get(BookInLibrary.class).readBooksByAuthor(id);
        } catch (OperationNotSupportedException e) {
            log.error(e);
            return "redirect:/library/1";
        }
        String masterString = "";
        if (authorDto.getFirstName()!=null) {
            masterString = authorDto.getFirstName();
        }
        if (authorDto.getSecondName()!=null) {
            masterString = masterString+authorDto.getSecondName();
        }
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(list);
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("authorId", id);
        model.addAttribute("master", masterString);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum( dtoList.size(), page));
        return "library/library_by_querry";
    }

    @GetMapping("/by_publisher/{id}/{page}")
    public String booksByPublisher(@PathVariable Long id, @PathVariable Integer page, Model model) {
        List<BookInLibrary> list ;
          PublisherDto publisherDto = (PublisherDto) frontMappersFactory.get(Publisher.class).map(serviceFactory.get(Publisher.class).read(id));
        try {
            list = serviceFactory.get(BookInLibrary.class).readBooksByPublisher(id);
        } catch (OperationNotSupportedException e) {
            log.error(e);
            return "redirect:/library/1";
        }
        String masterString = "";
        if (publisherDto.getName()!=null) {
            masterString = publisherDto.getName();
        }
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(list);
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("publisherId", id);
         model.addAttribute("master", masterString);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum( dtoList.size(), page));
        return "library/library_by_querry";
    }
    
    

    @PostMapping("/id/")
    public String submit(@ModelAttribute @Valid BookDto filledDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/library/1";
        }
        if (filledDto.getId() != null) {
            return "redirect:/library/1";
        }

        if (filledDto.getDescription()==""){
            filledDto.setDescription(null);
        }
        if (filledDto.getName()==""){
            filledDto.setName(null);
        }
        BookInLibrary bookInLibrary = (BookInLibrary) frontMappersFactory.get(BookInLibrary.class).map((filledDto));
        serviceFactory.get(BookInLibrary.class).save(bookInLibrary);
        return "redirect:/library/1";
    }

    @GetMapping("/id/{id}")
    public String openPersonalPage(@PathVariable Long id, Model model) {
        BookInLibrary bookInLibrary = (BookInLibrary) serviceFactory.get(BookInLibrary.class).read(id);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
        model.addAttribute("dto", dto);
        return "library/library_one";
    }

    @GetMapping("/id/library_new")
    public String newAccount(@ModelAttribute("emptyDto") BookDto emptyDto, Model model) {
        List<AuthorDto> authors = serviceFactory.get(Author.class).read();
        List<PublisherDto> publishers = serviceFactory.get(Publisher.class).read();
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);
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
        serviceFactory.get(BookInLibrary.class).update((EntityWithLongId) frontMappersFactory.get(BookInLibrary.class).map((dto)));
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
