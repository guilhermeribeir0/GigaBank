package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClienteRepositoryTest {

    private Cliente cliente;

    @Autowired
    private ClienteRepository repository;

    @BeforeEach
    void setup() {
        this.cliente = new Cliente();
        this.cliente.setNome("Guilherme");
        this.cliente.setCpf("66914105057");
        this.cliente.setEmail("guilherme@teste.com");
        this.cliente.setAtivo(true);
    }

    @Test
    void testBuscarClientePorId() {
        Cliente cli = this.cliente;
        repository.save(cli);

        Cliente clienteSalvo = repository.findById(cli.getId()).get();

        assertNotNull(clienteSalvo);
        assertEquals(cli.getId(), clienteSalvo.getId());
    }

    @Test
    void testBuscarClientePorCpf() {
        Cliente cli = this.cliente;
        repository.save(cli);

        Optional<Cliente> clienteSalvo = repository.findByCpf(cli.getCpf());

        assertTrue(clienteSalvo.isPresent());
        assertEquals(cli.getCpf(), clienteSalvo.get().getCpf());
    }

    @Test
    void testBuscarClientePorNome() {
        Cliente cli = this.cliente;
        repository.save(cli);

        List<Cliente> clientes = repository.findByNome(cli.getNome());

        assertEquals(1, clientes.size());
    }

    @Test
    void testBuscarTodosClientes() {
        Cliente cli = this.cliente;
        repository.save(cli);

        List<Cliente> clientes = repository.findAll();

        assertEquals(1, clientes.size());
    }

    @Test
    void testSalvarCliente() {
        Cliente cli = this.cliente;
        Cliente clienteSaved = repository.save(cli);

        assertNotNull(clienteSaved);
        assertTrue(clienteSaved.getId() > 0);
    }

    @Test
    void testAtualizarCliente() {
        Cliente cli = this.cliente;
        Cliente clienteSaved = repository.save(cli);

        clienteSaved.setEmail("guilherme@teste.com.br");
        repository.save(clienteSaved);

        assertEquals("guilherme@teste.com.br", clienteSaved.getEmail());
    }

    @Test
    void testDeletarCliente() {
        Cliente cli = this.cliente;
        Cliente clienteSaved = repository.save(cli);

        repository.deleteById(clienteSaved.getId());
        Optional<Cliente> clienteExcluido = repository.findById(clienteSaved.getId());

        assertTrue(clienteExcluido.isEmpty());
    }
}
