package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cartao;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CartaoRepositoryTest {

    private Cartao cartao;

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @BeforeEach
    void setup() {
        cartao = new Cartao();
        ContaBancaria contaBancaria = ofContaBancaria();

        cartao.setNumero("4824000022440088");
        cartao.setCvv("244");
        cartao.setDataVencimento(LocalDate.now());
        cartao.setAtivo(true);
        cartao.setContaBancaria(contaBancaria);
    }

    @Test
    void testFindByNumero() {
        Cartao cartaoTest = this.cartao;
        repository.save(cartaoTest);

        Optional<Cartao> cartaoSalvo = repository.findByNumero(cartaoTest.getNumero());

        assertTrue(cartaoSalvo.isPresent());
        assertEquals(cartaoTest.getNumero(), cartaoSalvo.get().getNumero());
    }

    @Test
    void testFindByCvv() {
        Cartao cartaoTest = this.cartao;
        repository.save(cartaoTest);

        Optional<Cartao> cartaoSalvo = repository.findByCvv(cartaoTest.getCvv());

        assertTrue(cartaoSalvo.isPresent());
        assertEquals(cartaoTest.getCvv(), cartaoSalvo.get().getCvv());
    }

    @Test
    void testFindByNumeroAndCvvAndDataVencimento() {
        Cartao cartaoTest = this.cartao;
        repository.save(cartaoTest);

        Optional<Cartao> cartaoSalvo = repository.findByNumeroAndCvvAndDataVencimento(cartaoTest.getNumero(),
                                                                            cartaoTest.getCvv(),
                                                                            cartaoTest.getDataVencimento());

        assertTrue(cartaoSalvo.isPresent());
        assertEquals(cartaoTest.getNumero(), cartaoSalvo.get().getNumero());
        assertEquals(cartaoTest.getCvv(), cartaoSalvo.get().getCvv());
        assertEquals(cartaoTest.getDataVencimento(), cartaoSalvo.get().getDataVencimento());
    }

    @Test
    void testFindByContaBancariaId() {
        Cartao cartaoTest = this.cartao;
        repository.save(cartaoTest);

        Optional<Cartao> cartaoSalvo = repository.findByContaBancariaId(cartaoTest.getContaBancaria().getId());

        assertTrue(cartaoSalvo.isPresent());
        assertEquals(cartaoTest.getContaBancaria().getId(), cartaoSalvo.get().getContaBancaria().getId());
    }

    private Cliente ofCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Guilherme");
        cliente.setCpf("66914105057");
        cliente.setEmail("guilherme@teste.com");
        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }

    private ContaBancaria ofContaBancaria() {
        ContaBancaria contaBancaria = new ContaBancaria();
        Cliente cliente = ofCliente();
        contaBancaria.setCliente(cliente);
        contaBancaria.setNumero("224488");
        contaBancaria.setAgencia("2244");
        contaBancaria.setAtiva(true);
        return contaBancariaRepository.save(contaBancaria);
    }
}
