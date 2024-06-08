package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ContaBancariaRepositoryTest {

    private ContaBancaria contaBancaria;

    @Autowired
    private ContaBancariaRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        contaBancaria = new ContaBancaria();
        Cliente cliente = ofCliente();

        contaBancaria.setCliente(cliente);
        contaBancaria.setNumero("224488");
        contaBancaria.setAgencia("2244");
        contaBancaria.setAtiva(true);
        repository.save(contaBancaria);
    }

    @Test
    void testFindByAgenciaAndNumero() {
        ContaBancaria conta = repository.save(contaBancaria);

        Optional<ContaBancaria> contaSalva = repository.findByAgenciaAndNumero(conta.getAgencia(), conta.getNumero());

        assertTrue(contaSalva.isPresent());
        assertEquals(conta.getAgencia(), contaSalva.get().getAgencia());
        assertEquals(conta.getNumero(), contaSalva.get().getNumero());
    }

    @Test
    void testFindByClienteCpf() {
        ContaBancaria conta = repository.save(contaBancaria);

        Optional<ContaBancaria> contaSalva = repository.findByClienteCpf(conta.getCliente().getCpf());

        assertTrue(contaSalva.isPresent());
        assertEquals(conta.getCliente().getCpf(), contaSalva.get().getCliente().getCpf());
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
