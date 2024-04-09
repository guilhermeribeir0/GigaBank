package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.ContaBancariaRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.contaBancaria.ContaBancariaResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ClienteNaoEncontradoException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ClienteSemContaVinculadaException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ContaNaoCadastradaException;
import br.com.guilhermeRibeiro.backendGigaBank.mapper.contaBancaria.ContaBancariaResponseMapper;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ContaBancariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;
    private final ContaBancariaResponseMapper responseMapper;
    private final ClienteService clienteService;
    private final CartaoService cartaoService;

    public ContaBancariaService(
            ContaBancariaRepository contaBancariaRepository,
            ContaBancariaResponseMapper responseMapper,
            ClienteService clienteService,
            CartaoService cartaoService
    ) {
        this.contaBancariaRepository = contaBancariaRepository;
        this.responseMapper = responseMapper;
        this.clienteService = clienteService;
        this.cartaoService = cartaoService;
    }

    public List<ContaBancaria> listaTodasAsContas() {
        List<ContaBancaria> listaContas = contaBancariaRepository.findAll();
        return listaContas;
    }

    public ContaBancaria buscarContaPorAgenciaENumero(String agencia, String numeroConta) {
        ContaBancaria contaBancaria = contaBancariaRepository.findByAgenciaAndNumero(agencia, numeroConta);
        if (Objects.isNull(contaBancaria)) {
            throw new ContaNaoCadastradaException(agencia, numeroConta);
        }
        return contaBancaria;
    }

    public ContaBancaria buscarContaPorCliente(String cpfCliente) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpfCliente);
        if (Objects.isNull(cliente)) {
            throw new ClienteNaoEncontradoException(cpfCliente);
        }
        ContaBancaria contaBancaria = contaBancariaRepository.findByClienteCpf(cpfCliente);
        if (Objects.isNull(contaBancaria)) {
            throw new ClienteSemContaVinculadaException();
        }
        return contaBancaria;
    }

    public ContaBancariaResponse cadastrarContaBancaria(ContaBancariaRequest request) {
        Cliente cliente = clienteService.buscarClientePorCpf(request.getClienteCpf());
        if (Objects.isNull(cliente)) {
            throw new ClienteNaoEncontradoException(request.getClienteCpf());
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
