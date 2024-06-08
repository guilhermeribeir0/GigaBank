package br.com.guilhermeRibeiro.backendGigaBank.repository;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extrato;
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
public class ExtratoRepositoryTest {

    private Extrato extrato;

    @Autowired
    private ExtratoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @BeforeEach
    void setup() {
        extrato = new Extrato();
        ContaBancaria contaBancaria = ofContaBancaria();
        extrato.setContaBancaria(contaBancaria);
        extrato.setTipoOperacao(TipoOperacaoUtil.SAQUE);
        extrato.setValor(BigDecimal.ONE);
        extrato.setDataOperacao(LocalDateTime.now());
    }

    @Test
    void testFindByContaBancariaId() {
        Extrato extratoUm = extrato;

        repository.save(extratoUm);

        List<Extrato> extratoList = repository.findByContaBancariaId(extratoUm.getContaBancaria().getId());

        assertFalse(extratoList.isEmpty());
        assertEquals(1, extratoList.size());
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
