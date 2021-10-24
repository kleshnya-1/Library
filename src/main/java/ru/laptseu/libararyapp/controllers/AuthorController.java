package ru.laptseu.libararyapp.controllers;//package ru.laptseu.libararyapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.entities.dto.AuthorDto;
import ru.laptseu.libararyapp.services.ServiceFactory;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final ServiceFactory serviceFactory;

    @GetMapping("/{page}")
    public String openPage(@PathVariable Integer page, Model model) {
        List<AuthorDto> dtoList = serviceFactory.get(Author.class).readDtoList(page);
        model.addAttribute("dtoList", dtoList);
        if (page == 1) {
            model.addAttribute("exPageNum", page);
        } else {
            model.addAttribute("exPageNum", page - 1);
        }
        model.addAttribute("nextPageNum", page + 1);
        return "authors/author_first";
    }

    @PostMapping("/id/")
    public String submit(@ModelAttribute AuthorDto filledDto) {
        Author author = (Author) serviceFactory.get(Author.class).fromDto(filledDto);
        serviceFactory.get(Author.class).save(author);
        return "redirect:/authors/1";
    }

    @GetMapping("/id/{id}")
    public String openAccount(@PathVariable Long id, Model model) {
        AuthorDto dto = (AuthorDto) serviceFactory.get(Author.class).readDto(id);
        model.addAttribute("dto", dto);
        return "authors/author_one";
    }

    //
    @GetMapping("/authors_new")
    public String newAccount(@ModelAttribute("emptyDto") AuthorDto emptyDto) {
        return "authors/author_new";
    }

    //
//    @GetMapping("/id/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("account", accountService.readDto(id));
//        List<BankDto> bankDto = bankService.readDto();
//        List<ClientDto> clientDtos = clientService.readDto();
//        model.addAttribute("bankModel", bankDto);
//        model.addAttribute("clientModel", clientDtos);
//        return "authors/author_edit";
//    }
//
//    @PatchMapping("/id/{id}")
//    public String update(@ModelAttribute("account") AuthorDto dto) {
//        accountService.update(accountService.fromDto(accountDto));
//        return "redirect:/authors/";
//    }
//
    @DeleteMapping("/id/{id}")
    public String delete(@PathVariable Long id) {
        serviceFactory.get(Author.class).delete(id);
        return "redirect:/authors/1";
    }
}
