package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.CustomerMinRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.CustomerRequest;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Customer;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ClienteNaoEncontradoException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.CpfInvalidoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.CustomerRepository;
import br.com.guilhermeRibeiro.backendGigaBank.util.CpfUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository clienteRepository) {
        this.customerRepository = clienteRepository;
    }

    @Transactional
    public List<Customer> listarTodosOsClientes() {
        return customerRepository.findAll();
    }

    public Customer buscarClientePorId(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ClienteNaoEncontradoException(id);
        }
        return customer.get();
    }

    @Transactional
    public Customer buscarClientePorCpf(String cpf) {
        if (!CpfUtil.validaCPF(cpf)) {
            throw new CpfInvalidoException(cpf);
        }

        Optional<Customer> customer = customerRepository.findByCpf(cpf);
        if (customer.isEmpty()) {
            throw new ClienteNaoEncontradoException(cpf);
        }
        return customer.get();
    }

    @Transactional
    public Customer cadastrarCliente(CustomerRequest request) {
        try {
            Customer customer = new Customer(request);
            return customerRepository.save(customer);
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao cadastrar o cliente" + exception.getMessage());
        }
    }

    @Transactional
    public Customer atualizarCadastroCliente(Long id, CustomerMinRequest request) {
        Optional<Customer> customer = customerRepository.findById(id);
        
        if (customer.isEmpty()) {
            throw new ClienteNaoEncontradoException(id);
        }

        customer.get().setName(request.getName());
        customer.get().setEmail(request.getEmail());

        return customerRepository.save(customer.get());
    }

    public Customer desativarOuAtivarCliente(Long id) {
        return customerRepository.findById(id)
                    .map(cli -> {
                        cli.changeActive();
                        return customerRepository.save(cli);
                    }).orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

}
