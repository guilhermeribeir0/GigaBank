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

    public ResponseEntity<List<Cliente>> listarTodosOsClientes() {
        List<Cliente> todosOsCLientes = clienteRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(todosOsCLientes);
    }

    public ResponseEntity<Cliente> buscarClientePorCpf(String cpf) {
        boolean cpfValido = CpfUtil.validaCPF(cpf);
        if (!cpfValido) {
            throw new ValidacaoException("CPF inválido: " + cpf);
        }
        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cliente);
        }
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    public ResponseEntity<List<ClienteDTO>> buscarClientePorNome(String nome) {
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

        return ResponseEntity.status(HttpStatus.OK).body(listaRetornoDTO);
    }

    public ResponseEntity<Cliente> cadastrarCliente(ClienteDTO clienteDTO) {
        try {
            Cliente cliente = new Cliente();
            cliente.setNome(clienteDTO.getNome());
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setAtivo(clienteDTO.getAtivo());
            clienteRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);

        } catch (NullPointerException exception) {
            throw new RegraDeNegocioException(exception.getMessage());
        }
    }

    public ResponseEntity<Cliente> atualizarCadastroCliente(String cpf, ClienteDTO clienteDTO) {
        Cliente clienteAtualizado = clienteRepository.findByCpf(cpf);
        if (clienteAtualizado == null) {
            throw new RegraDeNegocioException("Cliente não encontrado na base da dados");
        } else {
            clienteAtualizado.setCpf(clienteDTO.getCpf());
            clienteAtualizado.setNome(clienteDTO.getNome());
            clienteAtualizado.setEmail(clienteDTO.getEmail());
            clienteAtualizado.setAtivo(clienteDTO.getAtivo());
            clienteRepository.save(clienteAtualizado);

            return ResponseEntity.status(HttpStatus.OK).body(clienteAtualizado);
        }
    }

    public ResponseEntity<String> deletarCadastroCliente(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente == null) {
            throw new RegraDeNegocioException("Cliente não encontrado na base da dados");
        } else {
            clienteRepository.delete(cliente);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cadastro do cliente excluido da base de dados");
        }
    }

}
