package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ContaBancariaDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ContaBancariaService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CartaoService cartaoService;

    public List<ContaBancaria> listaTodasAsContas() {
        List<ContaBancaria> listaContas = contaBancariaRepository.findAll();
        return listaContas;
    }

    public ContaBancaria buscarContaPorAgenciaENumero(String agencia, String numeroConta) {
        ContaBancaria contaBancaria = contaBancariaRepository.findByAgenciaAndNumero(agencia, numeroConta);
        if (Objects.isNull(contaBancaria)) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        }
        return contaBancaria;
    }

    public ContaBancaria buscarContaPorCliente(String cpfCliente) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpfCliente);
        if (Objects.isNull(cliente)) {
            throw new RuntimeException(ValidacaoException.CLIENTE_NAO_CADASTRADO_EXCEPTION);
        }
        ContaBancaria contaBancaria = contaBancariaRepository.findByClienteCpf(cpfCliente);
        if (Objects.isNull(contaBancaria)) {
            throw new RuntimeException(ValidacaoException.CLIENTE_SEM_CONTA_VINCULADA_EXCEPTION);
        }
        return contaBancaria;
    }

    public ContaBancaria cadastrarContaBancaria(ContaBancariaDTO contaBancariaDTO) {
        Cliente cliente = clienteService.buscarClientePorCpf(contaBancariaDTO.getClienteCpf());
        if (Objects.isNull(cliente)) {
            throw new RuntimeException(ValidacaoException.CLIENTE_NAO_CADASTRADO_EXCEPTION);
        }
        ContaBancaria contaBancaria = new ContaBancaria(contaBancariaDTO, cliente);
        contaBancariaRepository.save(contaBancaria);
        cartaoService.cadastrar(contaBancaria);
        return contaBancaria;
    }
}
