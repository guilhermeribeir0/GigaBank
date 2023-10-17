package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.ContaBancariaRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.contaBancaria.ContaBancariaResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.mapper.contaBancaria.ContaBancariaResponseMapper;
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

    @Autowired
    private ContaBancariaResponseMapper responseMapper;

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

    public ContaBancariaResponse cadastrarContaBancaria(ContaBancariaRequest request) {
        Cliente cliente = clienteService.buscarClientePorCpf(request.getClienteCpf());
        if (Objects.isNull(cliente)) {
            throw new RuntimeException(ValidacaoException.CLIENTE_NAO_CADASTRADO_EXCEPTION);
        }
        ContaBancaria contaBancaria = new ContaBancaria(request, cliente);
        contaBancariaRepository.save(contaBancaria);
        cartaoService.cadastrar(contaBancaria);
        return responseMapper.modelToResponse(contaBancaria);
    }

    public void atualizarSaldo(ContaBancaria contaBancaria) {
        contaBancariaRepository.save(contaBancaria);
    }
}
