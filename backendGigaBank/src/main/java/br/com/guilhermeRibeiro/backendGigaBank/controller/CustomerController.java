package br.com.guilhermeRibeiro.backendGigaBank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Customer;
import br.com.guilhermeRibeiro.backendGigaBank.service.CustomerService;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.CustomerMinRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.CustomerRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.cliente.CustomerResponse;
import br.com.guilhermeRibeiro.backendGigaBank.mapper.cliente.CustomerResponseMapper;

@RestController
@RequestMapping(value = "/customers/")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerResponseMapper responseMapper;

    public CustomerController(
            CustomerService clienteService,
            CustomerResponseMapper responseMapper
    ) {
        this.customerService = clienteService;
        this.responseMapper = responseMapper;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<CustomerResponse>> listaTodosOsClientes() {
        List<Customer> customerList = customerService.listarTodosOsClientes();
        List<CustomerResponse> responses = responseMapper.modelListToResponseList(customerList);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(value = "id/{id}")
    public @ResponseBody ResponseEntity<CustomerResponse> buscaPorId(@PathVariable Long id) {
        Customer customer = customerService.buscarClientePorId(id);
        CustomerResponse response = responseMapper.modelToResponse(customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "cpf/{cpf}")
    public @ResponseBody ResponseEntity<CustomerResponse> buscaPorCpf(@PathVariable String cpf) {
        Customer customer = customerService.buscarClientePorCpf(cpf);
        CustomerResponse response = responseMapper.modelToResponse(customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<CustomerResponse> cadastrar(@RequestBody CustomerRequest request) {
        Customer customer = customerService.cadastrarCliente(request);
        CustomerResponse response = responseMapper.modelToResponse(customer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "atualizar/{id}")
    public @ResponseBody ResponseEntity<CustomerResponse> atualizarCadastro(@PathVariable Long id, @RequestBody CustomerMinRequest minRequest) {
        Customer customer = customerService.atualizarCadastroCliente(id, minRequest);
        CustomerResponse response = responseMapper.modelToResponse(customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<CustomerResponse> desativarOuAtivarCadastro(@PathVariable Long id) {
        Customer customer = customerService.desativarOuAtivarCliente(id);
        CustomerResponse response = responseMapper.modelToResponse(customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
