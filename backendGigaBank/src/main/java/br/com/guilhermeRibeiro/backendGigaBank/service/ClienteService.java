package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ClienteRepository;
import br.com.guilhermeRibeiro.backendGigaBank.util.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscarClientePorCpf(String cpf) {
        boolean cpfValido = CpfUtil.validaCPF(cpf);
        if (!cpfValido) {
            throw new RuntimeException("CPF Invalido: " + cpf);
        }
        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (cliente == null) {
            throw new RuntimeException("Nenhum cadastro encontrado para o CPF informado: " + cpf);
        }

        return cliente;
    }

    public Cliente buscarClientePorNome(String nome) {

    }
}
