package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Card;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CardRepositoryTest {

    private Card card;

    @Autowired
    private CardRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        card = new Card();
        Account account = ofContaBancaria();

        card.setNumber("4824000022440088");
        card.setCvv("244");
        card.setExpirationDate(LocalDate.now());
        card.setActive(true);
        card.setAccount(account);
    }

    @Test
    void testFindByNumber() {
        Card cardTest = this.card;
        repository.save(cardTest);

        Optional<Card> cartaoSalvo = repository.findByNumber(cardTest.getNumber());

        assertTrue(cartaoSalvo.isPresent());
        assertEquals(cardTest.getNumber(), cartaoSalvo.get().getNumber());
    }

    @Test
    void testFindByCvv() {
        Card cardTest = this.card;
        repository.save(cardTest);

        Optional<Card> cartaoSalvo = repository.findByCvv(cardTest.getCvv());

        assertTrue(cartaoSalvo.isPresent());
        assertEquals(cardTest.getCvv(), cartaoSalvo.get().getCvv());
    }

    @Test
    void testFindByNumberAndCvvAndDataVencimento() {
        Card cardTest = this.card;
        repository.save(cardTest);

        Optional<Card> cartaoSalvo = repository.findByNumberAndCvvAndExpirationDate(cardTest.getNumber(),
                                                                            cardTest.getCvv(),
                                                                            cardTest.getExpirationDate());

        assertTrue(cartaoSalvo.isPresent());
        assertEquals(cardTest.getNumber(), cartaoSalvo.get().getNumber());
        assertEquals(cardTest.getCvv(), cartaoSalvo.get().getCvv());
        assertEquals(cardTest.getExpirationDate(), cartaoSalvo.get().getExpirationDate());
    }

    @Test
    void testFindByAccountId() {
        Card cardTest = this.card;
        repository.save(cardTest);

        Optional<Card> cartaoSalvo = repository.findByAccountId(cardTest.getAccount().getId());

        assertTrue(cartaoSalvo.isPresent());
        assertEquals(cardTest.getAccount().getId(), cartaoSalvo.get().getAccount().getId());
    }

    private Cliente ofCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Guilherme");
        cliente.setCpf("66914105057");
        cliente.setEmail("guilherme@teste.com");
        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }

    private Account ofContaBancaria() {
        Account account = new Account();
        Cliente cliente = ofCliente();
        account.setCustomer(cliente);
        account.setNumber("224488");
        account.setAgency("2244");
        account.setActive(true);
        return accountRepository.save(account);
    }
}
