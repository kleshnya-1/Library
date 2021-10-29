package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
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

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {// TODO: 27.10.2021 нормальные названия методов
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;


    @GetMapping("/{page}")
    public String openPage(@PathVariable Integer page, Model model) {
        List<PublisherDto> dtoList = frontMappersFactory.get(Publisher.class).map(serviceFactory.get(Publisher.class).readList(page));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum( dtoList.size(), page));
        return "publishers/publisher_first";
    }

    @PostMapping("/id/")
    public String submit(@ModelAttribute @Valid PublisherDto filledDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/publishers/1";
        }
        if (filledDto.getId() != null) {//this URL only for new Entities
            return "redirect:/publishers/1";
        }
        Publisher publisher = (Publisher) frontMappersFactory.get(Publisher.class).map((filledDto));
        serviceFactory.get(Publisher.class).save(publisher);
        return "redirect:/publishers/1";
    }

    @GetMapping("/id/{id}")
    public String openPersonalPage(@PathVariable Long id, Model model) {
        Publisher publisher = (Publisher) serviceFactory.get(Publisher.class).read(id);
        PublisherDto dto = (PublisherDto) frontMappersFactory.get(Publisher.class).map(publisher);
        model.addAttribute("dto", dto);
        return "publishers/publisher_one";
    }

    @GetMapping("/id/publishers_new")
    public String newAccount(@ModelAttribute("emptyDto") PublisherDto emptyDto) {
        return "publishers/publisher_new";
    }

    @GetMapping("/id/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        Publisher publisher = (Publisher) serviceFactory.get(Publisher.class).read(id);
        AuthorDto dto = (AuthorDto) frontMappersFactory.get(Publisher.class).map(publisher);
        model.addAttribute("dto", dto);
        return "publishers/publisher_edit";
    }

    @PatchMapping("/id/{id}")
    public String update(@ModelAttribute("dto") AuthorDto dto) {
        serviceFactory.get(Publisher.class).update((EntityWithLongId) frontMappersFactory.get(Publisher.class).map((dto)));
        return "redirect:/publishers/1";
    }

    @PostMapping("/id/{id}/remove")
    public String delete(@PathVariable Long id) {
        serviceFactory.get(Publisher.class).delete(id);
        return "redirect:/publishers/1";
    }
}
