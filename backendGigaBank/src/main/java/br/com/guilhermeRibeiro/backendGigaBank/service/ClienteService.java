package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteMinDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ClienteRepository;
import br.com.guilhermeRibeiro.backendGigaBank.util.CpfUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Cliente buscarClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            throw new RuntimeException(ValidacaoException.CLIENTE_NAO_CADASTRADO_EXCEPTION);
        }
        return cliente.get();
    }

    public Cliente buscarClientePorCpf(String cpf) {
        boolean cpfValido = CpfUtil.validaCPF(cpf);
        if (!cpfValido) {
            throw new RuntimeException(ValidacaoException.CPF_INVALIDO_EXCEPTION + cpf);
        }
        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (cliente == null) {
            throw new RuntimeException(ValidacaoException.CLIENTE_NAO_CADASTRADO_EXCEPTION);
        }
        return cliente;
    }

    public List<ClienteDTO> buscarClientePorNome(String nome) {
        if (StringUtils.isBlank(nome) || StringUtils.isNumeric(nome)) {
            throw new RuntimeException(ValidacaoException.NOME_INVALIDO_EXCEPTION);
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
            Cliente cliente = new Cliente(clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getEmail(), clienteDTO.getAtivo());
            clienteRepository.save(cliente);
            return cliente;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Cliente atualizarCadastroCliente(Long id, ClienteMinDTO clienteMinDTO) {
        Optional<Cliente> cliente = Optional.of(clienteRepository.findById(id)
                .map(clienteAtualizado -> {
                    clienteAtualizado.setNome(clienteMinDTO.getNome());
                    clienteAtualizado.setEmail(clienteMinDTO.getEmail());
                    clienteRepository.save(clienteAtualizado);
                    clienteAtualizado.setCpf("***"+clienteAtualizado.getCpf().substring(3, 9)+"**");
                    return clienteAtualizado;
                }).orElseThrow(() -> new RuntimeException(ValidacaoException.CLIENTE_NAO_CADASTRADO_EXCEPTION)));
        return cliente.get();
    }

    public Cliente desativarOuAtivarCliente(Long id) {
        Optional<Cliente> cliente = Optional.of(clienteRepository.findById(id)
                .map(cli -> {
                    if (!cli.getAtivo()) {
                        cli.setAtivo(true);
                    }
                    cli.setAtivo(false);
                    clienteRepository.save(cli);
                    return cli;
                }).orElseThrow(() -> new RuntimeException(ValidacaoException.CLIENTE_NAO_CADASTRADO_EXCEPTION)));
        return cliente.get();
    }

}
