package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryTest {

    private Account account;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        account = new Account();
        Cliente cliente = ofCliente();

        account.setCustomer(cliente);
        account.setNumber("224488");
        account.setAgency("2244");
        account.setActive(true);
        repository.save(account);
    }

    @Test
    void testFindByAgenciaAndNumero() {
        Account conta = repository.save(account);

        Optional<Account> contaSalva = repository.findByAgencyAndNumber(conta.getAgency(), conta.getNumber());

        assertTrue(contaSalva.isPresent());
        assertEquals(conta.getAgency(), contaSalva.get().getAgency());
        assertEquals(conta.getNumber(), contaSalva.get().getNumber());
    }

    @Test
    void testFindByClienteCpf() {
        Account conta = repository.save(account);

        Optional<Account> contaSalva = repository.findByCustomerCpf(conta.getCustomer().getCpf());

        assertTrue(contaSalva.isPresent());
        assertEquals(conta.getCustomer().getCpf(), contaSalva.get().getCustomer().getCpf());
    }

    private Cliente ofCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Guilherme");
        cliente.setCpf("66914105057");
        cliente.setEmail("guilherme@teste.com");
        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }
}
