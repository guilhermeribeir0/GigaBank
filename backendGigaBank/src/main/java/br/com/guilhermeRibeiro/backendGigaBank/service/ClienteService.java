package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.exception.RegraDeNegocioException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ClienteRepository;
import br.com.guilhermeRibeiro.backendGigaBank.util.CpfUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodosOsClientes() {
        List<Cliente> todosOsCLientes = clienteRepository.findAll();

        List<Cliente> listaClientesCpfMascarado = new ArrayList<>();
        for (Cliente cliente : todosOsCLientes) {
            Cliente clienteCpfMascarado = new Cliente();
            BeanUtils.copyProperties(cliente, clienteCpfMascarado);
            clienteCpfMascarado.setCpf("***"+cliente.getCpf().substring(3, 9)+"**");
            listaClientesCpfMascarado.add(clienteCpfMascarado);
        }
        return listaClientesCpfMascarado;
    }

    public Cliente buscarClientePorCpf(String cpf) {
        boolean cpfValido = CpfUtil.validaCPF(cpf);
        if (!cpfValido) {
            throw new ValidacaoException("CPF inválido: " + cpf);
        }
        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (cliente == null) {
            throw new ValidacaoException("Cliente não cadastrado na base de dados");
        }
        return cliente;
    }

    public List<ClienteDTO> buscarClientePorNome(String nome) {
        if (StringUtils.isBlank(nome) || StringUtils.isNumeric(nome)) {
            throw new ValidacaoException("Nome informado é inválido");
        }
        List<Cliente> listaClientes = clienteRepository.findByNome(nome);
        List<ClienteDTO> listaRetornoDTO = new ArrayList<>();
        for (Cliente cliente : listaClientes) {
            ClienteDTO clienteDTO = new ClienteDTO();
            BeanUtils.copyProperties(cliente, clienteDTO);
            clienteDTO.setCpf("***"+cliente.getCpf().substring(3, 9)+"**");
            listaRetornoDTO.add(clienteDTO);
        }

        return listaRetornoDTO;
    }

    public Cliente cadastrarCliente(ClienteDTO clienteDTO) {
        try {
            Cliente cliente = new Cliente();
            cliente.setNome(clienteDTO.getNome());
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setAtivo(clienteDTO.getAtivo());
            clienteRepository.save(cliente);
            return cliente;

        } catch (NullPointerException exception) {
            throw new RegraDeNegocioException(exception.getMessage());
        }
    }

    public Cliente atualizarCadastroCliente(String cpf, ClienteDTO clienteDTO) {
        Cliente clienteAtualizado = clienteRepository.findByCpf(cpf);
        if (clienteAtualizado == null) {
            throw new RegraDeNegocioException("Cliente não encontrado na base da dados");
        } else {
            clienteAtualizado.setCpf(clienteDTO.getCpf());
            clienteAtualizado.setNome(clienteDTO.getNome());
            clienteAtualizado.setEmail(clienteDTO.getEmail());
            clienteAtualizado.setAtivo(clienteDTO.getAtivo());
            clienteRepository.save(clienteAtualizado);

            clienteAtualizado.setCpf("***"+clienteAtualizado.getCpf().substring(3, 9)+"**");
            return clienteAtualizado;
        }
    }

    public void deletarCadastroCliente(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente == null) {
            throw new ValidacaoException("Cliente não encontrado na base de dados");
        }
        clienteRepository.delete(cliente);
    }

}
