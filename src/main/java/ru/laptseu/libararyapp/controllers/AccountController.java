//package ru.laptseu.libararyapp.controllers;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.laptseu.bankapp.models.Account;
//import ru.laptseu.bankapp.models.dto.AccountDto;
//import ru.laptseu.bankapp.models.dto.BankDto;
//import ru.laptseu.bankapp.models.dto.ClientDto;
//import ru.laptseu.bankapp.services.AccountService;
//import ru.laptseu.bankapp.services.BankService;
//import ru.laptseu.bankapp.services.ClientService;
//
//import java.util.List;
//
//@Log4j2
//@Controller
//@RequestMapping("/accounts")
//@RequiredArgsConstructor
//public class AccountController {
//    private final AccountService accountService;
//    private final BankService bankService;
//    private final ClientService clientService;
//
//    @GetMapping("/")
//    public String openAllAccounts(Model model) {
//        List<AccountDto> accountDtos = accountService.readDto();
//        model.addAttribute("accounts", accountDtos);
//        return "accounts/show";
//    }
//
//    @PostMapping("/")
//    public String submit(@ModelAttribute AccountDto newb) {
//        Account newAccount = accountService.fromDto(newb);
//        accountService.save(newAccount);
//        return "redirect:/accounts/";
//    }
//
//    @GetMapping("/{id}")
//    public String openAccount(@PathVariable Integer id, Model model) {
//        AccountDto dt = accountService.readDto(id);
//        model.addAttribute("account", dt);
//        return "accounts/showOne";
//    }
//
//    @GetMapping("/new_account")
//    public String newAccount(@ModelAttribute("newb") AccountDto newb, Model model) {
//        List<BankDto> bankDto = bankService.readDto();
//        List<ClientDto> clientDtos = clientService.readDto();
//        model.addAttribute("bankModel", bankDto);
//        model.addAttribute("clientModel", clientDtos);
//        return "accounts/new";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("account", accountService.readDto(id));
//        List<BankDto> bankDto = bankService.readDto();
//        List<ClientDto> clientDtos = clientService.readDto();
//        model.addAttribute("bankModel", bankDto);
//        model.addAttribute("clientModel", clientDtos);
//        return "accounts/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("account") AccountDto accountDto) {
//        accountService.update(accountService.fromDto(accountDto));
//        return "redirect:/accounts/";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteAccount(@PathVariable Integer id) {
//        accountService.delete(id);
//        return "redirect:/accounts/";
//    }
//}
