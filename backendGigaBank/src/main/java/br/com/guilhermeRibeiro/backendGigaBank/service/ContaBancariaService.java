package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ContaBancariaDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaBancariaService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private ClienteService clienteService;

    public List<ContaBancaria> listaTodasAsContas() {
        List<ContaBancaria> listaContas = contaBancariaRepository.findAll();
        return listaContas;
    }

    public ContaBancaria buscarContaPorAgenciaENumero(String agencia, String numeroConta) {
        ContaBancaria contaBancaria = contaBancariaRepository.findByAgenciaAndNumero(agencia, numeroConta);
        if (contaBancaria == null) {
            throw new ValidacaoException("Conta bancária não encontrada");
        }

        return contaBancaria;
    }

    public ContaBancaria cadastrarContaBancaria(ContaBancariaDTO contaBancariaDTO) {
        Cliente cliente = clienteService.buscarClientePorCpf(contaBancariaDTO.getClienteCpf());
        if (cliente == null) {
            throw new ValidacaoException("Cliente não encontrado");
        }

        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setAgencia(contaBancariaDTO.getAgencia());
        contaBancaria.setNumero(contaBancariaDTO.getNumero());
        contaBancaria.setSaldo(contaBancariaDTO.getSaldo());
        contaBancaria.setCliente(cliente);
        contaBancaria.setAtiva(contaBancariaDTO.getAtiva());
        contaBancariaRepository.save(contaBancaria);

        return contaBancaria;
    }
}
