package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingEntityLoader;
import ru.laptseu.libararyapp.mappers.backMappers.BookArchivingMapper;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.models.dto.BookDto;
import ru.laptseu.libararyapp.models.entities.BookArchived;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.services.ServiceFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;
import ru.laptseu.libararyapp.utilities.TextTrimmingUtility;

import javax.naming.OperationNotSupportedException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/archive")
@RequiredArgsConstructor
public class BookArchivedController {
    private static final String startingUrl = "redirect:/archive/1";
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;
    private final BookArchivingMapper bookArchivingMapper;
    private final TextTrimmingUtility textTrimmingUtility;
    private final BookArchivingEntityLoader bookArchivingEntityLoader;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss/MM-dd-yyyy");

    @GetMapping("/{page}")
    public String getBooksInArchive(@PathVariable Integer page, Model model) {
        Pageable pageable = pageUtility.getPageable(page);
        List l = bookArchivingEntityLoader.findEntity(bookArchivingMapper.map(serviceFactory.get(BookArchived.class).readList(pageable)));
        List<BookDto> dtoList = frontMappersFactory.get(BookInLibrary.class).map(l);
        dtoList.forEach(bookDto -> bookDto.setDescription(textTrimmingUtility.trimToSize(bookDto.getDescription())));
        dtoList.stream().filter(bookDto -> bookDto.getId() == null).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("url", "archive");
        model.addAttribute("currentPageNum", page);
        model.addAttribute("isLastPage", pageUtility.getIsTheLastPage(BookArchived.class, dtoList.size(), page));
        return "archive/archive_first";
    }

    @GetMapping("/id/{id}")
    public String getBookInArchive(@PathVariable Long id, Model model) {
        BookArchived bookArchived = (BookArchived) serviceFactory.get(BookArchived.class).read(id);
        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
        bookInLibrary = bookArchivingEntityLoader.findEntity(bookInLibrary);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
        model.addAttribute("dto", dto);
        model.addAttribute("dateOfArchiving", simpleDateFormat.format(bookArchived.getDateOfArchived().getTime()));
        return "archive/archive_one";
    }

    @GetMapping("/id/{id}/edit")
    public String editBookInArchive(Model model, @PathVariable("id") Long id) {
        BookArchived bookArchived = (BookArchived) serviceFactory.get(BookArchived.class).read(id);
        BookInLibrary bookInLibrary = bookArchivingMapper.map(bookArchived);
        BookDto dto = (BookDto) frontMappersFactory.get(BookInLibrary.class).map(bookInLibrary);
        model.addAttribute("dto", dto);
        model.addAttribute("dateOfArchiving", simpleDateFormat.format(bookArchived.getDateOfArchived().getTime()));
        return "archive/archive_edit";
    }

    @PatchMapping("/id/{id}")
    public String updateBookInArchive(@ModelAttribute("dto") BookDto dto, @PathVariable("id") Long id) {
        BookArchived origin = (BookArchived) serviceFactory.get(BookArchived.class).read(id);
        if (origin.getDescription() != dto.getDescription()) {
            origin.setDescription(dto.getDescription());
            serviceFactory.get(BookArchived.class).update(origin);
        }
        return startingUrl;
    }

    @PostMapping("/id/{id}/remove")
    public String deleteBookInArchive(@PathVariable Long id) {
        serviceFactory.get(BookArchived.class).delete(id);
        return startingUrl;
    }

    @PostMapping("/id/{id}/to_library")
    public String unarchiveBookInArchive(@PathVariable Long id) {
        try {
            serviceFactory.get(BookArchived.class).fromArchive(id);
            return "archive/archive_successful_unarchiving";
        } catch (OperationNotSupportedException e) {
            log.error(e);
        }
        return startingUrl;
    }
}
