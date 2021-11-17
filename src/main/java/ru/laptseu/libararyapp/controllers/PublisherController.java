package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.models.dto.PublisherDto;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.Publisher;
import ru.laptseu.libararyapp.services.ServiceFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private static final String startingUrl = "redirect:/publishers/1";
    private final ServiceFactory serviceFactory;
    private final FrontMappersFactory frontMappersFactory;
    private final PageUtility pageUtility;
    private final Validator validator;

    @GetMapping("/{page}")
    public String getPublishers(@PathVariable Integer page, Model model) {
        Pageable pageable = pageUtility.getPageable(page);
        List<PublisherDto> dtoList = frontMappersFactory.get(Publisher.class).map(serviceFactory.get(Publisher.class).readList(pageable));
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("url", "publishers");
        model.addAttribute("currentPageNum", page);
        model.addAttribute("isLastPage", pageUtility.getIsTheLastPage(Author.class, dtoList.size(), page));
        return "publishers/publisher_first";
    }

    @PostMapping("/id/")
    public String createPublisher(@Valid PublisherDto filledDto, BindingResult bindingResult, Model model) {
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
            model.addAttribute("entity", "издателя");
            model.addAttribute("errors", errors);
            return "blocks/error_messages";
        }
        Publisher publisher = (Publisher) frontMappersFactory.get(Publisher.class).map((filledDto));
        serviceFactory.get(Publisher.class).save(publisher);
        return startingUrl;
    }

    @GetMapping("/id/{id}")
    public String getPublisher(@PathVariable Long id, Model model) {
        Publisher publisher = (Publisher) serviceFactory.get(Publisher.class).read(id);
        PublisherDto dto = (PublisherDto) frontMappersFactory.get(Publisher.class).map(publisher);
        model.addAttribute("dto", dto);
        model.addAttribute("bookList", dto.getBookList());
        return "publishers/publisher_one";
    }

    @GetMapping("/id/publishers_new")
    public String getCreatePublisherPage(@ModelAttribute("emptyDto") PublisherDto emptyDto) {
        return "publishers/publisher_new";
    }

    @GetMapping("/id/{id}/edit")
    public String editPublisher(Model model, @PathVariable("id") Long id) {
        Publisher publisher = (Publisher) serviceFactory.get(Publisher.class).read(id);
        PublisherDto dto = (PublisherDto) frontMappersFactory.get(Publisher.class).map(publisher);
        model.addAttribute("dto", dto);
        return "publishers/publisher_edit";
    }

    @PatchMapping("/id/{id}")
    public String updatePublisher(@ModelAttribute("dto") PublisherDto dto) {
        serviceFactory.get(Publisher.class).update(frontMappersFactory.get(Publisher.class).map((dto)));
        return startingUrl;
    }

    @PostMapping("/id/{id}/remove")
    public String deletePublisher(@PathVariable Long id) {
        serviceFactory.get(Publisher.class).delete(id);
        return startingUrl;
    }
}
