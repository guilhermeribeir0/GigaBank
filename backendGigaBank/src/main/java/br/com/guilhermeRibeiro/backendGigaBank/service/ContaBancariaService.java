package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.ContaBancariaRequest;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ClienteSemContaVinculadaException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ContaNaoCadastradaException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ContaBancariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;
    private final ClienteService clienteService;
    private final CartaoService cartaoService;

    public ContaBancariaService(
            ContaBancariaRepository contaBancariaRepository,
            ClienteService clienteService,
            CartaoService cartaoService
    ) {
        this.contaBancariaRepository = contaBancariaRepository;
        this.clienteService = clienteService;
        this.cartaoService = cartaoService;
    }

    public List<ContaBancaria> listaTodasAsContas() {
        List<ContaBancaria> listaContas = contaBancariaRepository.findAll();
        return listaContas;
    }

    public ContaBancaria buscarContaPorAgenciaENumero(String agencia, String numeroConta) {
        Optional<ContaBancaria> contaBancaria = contaBancariaRepository.findByAgenciaAndNumero(agencia, numeroConta);

        if (contaBancaria.isEmpty()) {
            throw new ContaNaoCadastradaException(agencia, numeroConta);
        }

        return contaBancaria.get();
    }

    public ContaBancaria buscarContaPorCliente(String cpfCliente) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpfCliente);
        Optional<ContaBancaria> contaBancaria = contaBancariaRepository.findByClienteCpf(cliente.getCpf());

        if (contaBancaria.isEmpty()) {
            throw new ClienteSemContaVinculadaException();
        }

        return contaBancaria.get();
    }

    public ContaBancaria cadastrarContaBancaria(ContaBancariaRequest request) {
        Cliente cliente = clienteService.buscarClientePorCpf(request.getClienteCpf());
        ContaBancaria contaBancaria = new ContaBancaria(request, cliente);

        contaBancariaRepository.save(contaBancaria);
        cartaoService.cadastrar(contaBancaria);

        return contaBancaria;
    }

    public void atualizarSaldo(ContaBancaria contaBancaria) {
        contaBancariaRepository.save(contaBancaria);
    }
}
