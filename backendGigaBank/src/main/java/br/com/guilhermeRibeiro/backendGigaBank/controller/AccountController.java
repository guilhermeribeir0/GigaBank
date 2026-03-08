package br.com.guilhermeRibeiro.backendGigaBank.controller;

import java.util.List;

import br.com.guilhermeRibeiro.backendGigaBank.dto.response.account.AccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.service.AccountService;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.AccountRequest;
import br.com.guilhermeRibeiro.backendGigaBank.mapper.account.AccountMapper;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper responseMapper;

    public AccountController(AccountService accountService,
                                   AccountMapper accountMapper) {
        this.accountService = accountService;
        this.responseMapper = accountMapper;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Account>> listAllAccounts() {
        List<Account> accountList = accountService.listAll();
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public @ResponseBody ResponseEntity<AccountResponse> registerAccount(@RequestBody AccountRequest request) {
        Account account = accountService.registerAccount(request);
        AccountResponse response = responseMapper.modelToResponse(account);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
