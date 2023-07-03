package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.exception.RegraDeNegocioException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ClienteRepository;
import br.com.guilhermeRibeiro.backendGigaBank.util.CpfUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscarClientePorCpf(String cpf) {
        boolean cpfValido = CpfUtil.validaCPF(cpf);
        if (!cpfValido) {
            throw new ValidacaoException("CPF inválido: " + cpf);
        }
        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (cliente == null) {
            throw new RegraDeNegocioException("Nenhum cadastro encontrado para o CPF informado: " + cpf);
        }

        return cliente;
    }

    public List<Cliente> buscarClientePorNome(String nome) {
        if (nome == null) {
            throw new ValidacaoException("Informe um nome");
        }

        List<Cliente> clientes = clienteRepository.findByNome(nome);

        if (clientes.isEmpty()) {
            throw new RegraDeNegocioException("Cliente não encontrado" + nome);
        }

        return clientes;
    }

    public Cliente cadastrarCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getNome() == null || clienteDTO.getCpf() == null || clienteDTO.getEmail() == null) {
            throw new ValidacaoException("Verifique as informações e tente novamente");
        }

        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(cliente, clienteDTO);
        clienteRepository.save(cliente);

        return cliente;
    }

}
