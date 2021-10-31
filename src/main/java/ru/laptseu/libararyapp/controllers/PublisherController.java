package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.entities.EntityWithLongId;
import ru.laptseu.libararyapp.entities.Publisher;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;
import ru.laptseu.libararyapp.entities.dto.PublisherDto;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.services.ServiceFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {// TODO: 27.10.2021 нормальные названия методов
    private static final String startingUrl = "redirect:/publishers/1";
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;

    @GetMapping("/{page}")
    public String openPage(@PathVariable Integer page, Model model) {
        List<PublisherDto> dtoList = frontMappersFactory.get(Publisher.class).map(serviceFactory.get(Publisher.class).readList(page));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("exPageNum", pageUtility.getExPageNum(page));
        model.addAttribute("nextPageNum", pageUtility.getNextPageNum(dtoList.size(), page));
        return "publishers/publisher_first";
    }

    @PostMapping("/id/")
    public String submit(@ModelAttribute @Valid PublisherDto filledDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors());
            return startingUrl;
        }
        if (filledDto.getId() != null) {//this URL only for new Entities
            log.error("not saved as new with id " + filledDto.getId());
            return startingUrl;
        }
        Publisher publisher = (Publisher) frontMappersFactory.get(Publisher.class).map((filledDto));
        serviceFactory.get(Publisher.class).save(publisher);
        return startingUrl;
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
        return startingUrl;
    }

    @PostMapping("/id/{id}/remove")
    public String delete(@PathVariable Long id) {
        serviceFactory.get(Publisher.class).delete(id);
        return startingUrl;
    }
}
