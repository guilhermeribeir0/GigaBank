package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.ClienteMinRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.ClienteRequest;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ClienteNaoEncontradoException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.CpfInvalidoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ClienteRepository;
import br.com.guilhermeRibeiro.backendGigaBank.util.CpfUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodosOsClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            throw new ClienteNaoEncontradoException(id);
        }
        return cliente.get();
    }

    public Cliente buscarClientePorCpf(String cpf) {
        boolean cpfValido = CpfUtil.validaCPF(cpf);
        if (!cpfValido) {
            throw new CpfInvalidoException(cpf);
        }
        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (Objects.isNull(cliente)) {
            throw new ClienteNaoEncontradoException(cpf);
        }
        return cliente;
    }

    public Cliente cadastrarCliente(ClienteRequest request) {
        try {
            Cliente cliente = new Cliente(request);
            return clienteRepository.save(cliente);
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao cadastrar o cliente" + exception.getMessage());
        }
    }

    public Cliente atualizarCadastroCliente(Long id, ClienteMinRequest request) {
        Optional<Cliente> cliente = Optional.of(clienteRepository.findById(id)
                .map(clienteAtualizado -> {
                    clienteAtualizado.setEmail(request.getEmail());
                    clienteAtualizado.setNome(request.getNome());
                    return clienteRepository.save(clienteAtualizado);
                }).orElseThrow(() -> new ClienteNaoEncontradoException(id)));
        return cliente.get();
    }

    public Cliente desativarOuAtivarCliente(Long id) {
        Optional<Cliente> cliente = Optional.of(clienteRepository.findById(id)
                .map(cli -> {
                    if (!cli.getAtivo()) {
                        cli.setAtivo(true);
                    } else {
                        cli.setAtivo(false);
                    }
                    clienteRepository.save(cli);
                    return cli;
                }).orElseThrow(() -> new ClienteNaoEncontradoException(id)));
        return cliente.get();
    }

}
