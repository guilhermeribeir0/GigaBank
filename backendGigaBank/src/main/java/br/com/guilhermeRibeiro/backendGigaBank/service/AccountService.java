package br.com.guilhermeRibeiro.backendGigaBank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Customer;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ClienteSemContaVinculadaException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ContaNaoCadastradaException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.AccountRepository;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.AccountRequest;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final CustomerService customerService;
    private final CardService cardService;

    public AccountService(
            AccountRepository repository,
            CustomerService customerService,
            CardService cardService
    ) {
        this.repository = repository;
        this.customerService = customerService;
        this.cardService = cardService;
    }

    public List<Account> listAll() {
        return repository.findAll();
    }

    public Account findAccountByAgencyAndNumber(String agency, String number) {
        Optional<Account> account = repository.findByAgencyAndNumber(agency, number);

        if (account.isEmpty()) {
            throw new ContaNaoCadastradaException(agency, number);
        }

        return account.get();
    }

    public Account findAccountByCustomer(String cpfCliente) {
        Customer customer = customerService.buscarClientePorCpf(cpfCliente);
        Optional<Account> account = repository.findByCustomerCpf(customer.getCpf());

        if (account.isEmpty()) {
            throw new ClienteSemContaVinculadaException();
        }

        return account.get();
    }

    public Account registerAccount(AccountRequest request) {
        Customer customer = customerService.buscarClientePorCpf(request.getCpfCustomer());
        Account account = new Account(request, customer);

        repository.save(account);
        cardService.create(account);

        return account;
    }

    public void updateBalance(Account account) {
        repository.save(account);
    }
}
