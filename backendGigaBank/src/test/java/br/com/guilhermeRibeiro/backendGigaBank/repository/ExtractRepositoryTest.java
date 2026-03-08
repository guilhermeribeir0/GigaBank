package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extract;
import br.com.guilhermeRibeiro.backendGigaBank.util.TipoOperacaoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ExtractRepositoryTest {

    private Extract extract;

    @Autowired
    private ExtratoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        extract = new Extract();
        Account account = ofContaBancaria();
        extract.setAccount(account);
        extract.setOperationType(TipoOperacaoUtil.SAQUE);
        extract.setValue(BigDecimal.ONE);
        extract.setOperationDate(LocalDateTime.now());
    }

    @Test
    void testFindByContaBancariaId() {
        Extract extractUm = extract;

        repository.save(extractUm);

        List<Extract> extractList = repository.findByContaBancariaId(extractUm.getAccount().getId());

        assertFalse(extractList.isEmpty());
        assertEquals(1, extractList.size());
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
