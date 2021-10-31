package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.books.BookArchived;
import ru.laptseu.libararyapp.entities.books.BookInLibrary;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;
import ru.laptseu.libararyapp.entities.dto.BookDto;
import ru.laptseu.libararyapp.entities.dto.PublisherDto;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.services.ServiceFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.naming.OperationNotSupportedException;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/archive")
@RequiredArgsConstructor
public class BookArchivedController {
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;
    private final BookArchivingMapper bookArchivingMapper;

    @GetMapping("/{page}")
    public String openPage(@PathVariable Integer page, Model model) {
        List<BookDto> dtoList =  frontMappersFactory.get(BookInLibrary.class).map(bookArchivingMapper.map(serviceFactory.get(BookArchived.class).readList(page)));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum( dtoList.size(), page));
    // TODO: 28.10.2021 th:text="@{{id}(id=${a.getFirstName()})} ref in html
        return "archive/archive_first";
    }

    @GetMapping("/id/{id}")
    public String openPersonalPage(@PathVariable Long id, Model model) {
        BookArchived bookArchived = (BookArchived) serviceFactory.get(BookArchived.class).read(id);
        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
        model.addAttribute("dto", dto);
        model.addAttribute("dateOfArchiving", bookArchived.getDateOfArchived().getTime());
        return "archive/archive_one";
    }

//    @GetMapping("/id/library_new")
//    public String newAccount(@ModelAttribute("emptyDto") BookDto emptyDto, Model model) {
//        List<AuthorDto> authors = serviceFactory.get(Author.class).read();
//        List<PublisherDto> publishers = serviceFactory.get(Publisher.class).read();
//        model.addAttribute("authors", authors);
//        model.addAttribute("publishers", publishers);
//        return "archive/library_new";
//    }

    @GetMapping("/id/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        BookArchived bookArchived = (BookArchived) serviceFactory.get(BookArchived.class).read(id);
        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
        List<AuthorDto> authors = frontMappersFactory.get(Author.class).map(bookInLibrary.getAuthorList());
        model.addAttribute("dto", dto);
        model.addAttribute("dtoList", authors);
        return "archive/archive_edit";
    }

    @PatchMapping("/id/{id}")
    public String update(@ModelAttribute("dto") BookDto dto) {
        serviceFactory.get(BookArchived.class).update(bookArchivingMapper.map((BookArchived) frontMappersFactory.get(BookInLibrary.class).map((dto))));
        return "redirect:/archive/1";
    }

    @PostMapping("/id/{id}/remove")
    public String delete(@PathVariable Long id) {
        serviceFactory.get(BookArchived.class).delete(id);
        return "redirect:/archive/1";
    }
    @PostMapping("/id/{id}/to_library")
    public String toLibrary(@PathVariable Long id) {
        try {
            serviceFactory.get(BookArchived.class).fromArchive(id);
            return "archive/archive_successful_unarchiving";
        } catch (OperationNotSupportedException e) {
            log.error(e);
        }
        return "redirect:/archive/1";
    }
}
