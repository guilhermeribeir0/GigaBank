package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.ClienteMinRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.ClienteRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.cliente.ClienteResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.mapper.cliente.ClienteResponseMapper;
import br.com.guilhermeRibeiro.backendGigaBank.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes/")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteResponseMapper responseMapper;

    public ClienteController(
            ClienteService clienteService,
            ClienteResponseMapper responseMapper
    ) {
        this.clienteService = clienteService;
        this.responseMapper = responseMapper;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<ClienteResponse>> listaTodosOsClientes() {
        List<Cliente> listaDeClientes = clienteService.listarTodosOsClientes();
        List<ClienteResponse> responses = responseMapper.modelListToResponseList(listaDeClientes);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(value = "cpf/{cpf}")
    public @ResponseBody ResponseEntity<Cliente> buscaPorCpf(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping(value = "nome/{nome}")
    public @ResponseBody ResponseEntity<List<ClienteResponse>> buscaPorNome(@PathVariable String nome) {
        List<Cliente> clientes = clienteService.buscarClientePorNome(nome);
        List<ClienteResponse> responses = responseMapper.modelListToResponseList(clientes);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<ClienteResponse> cadastrar(@RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.cadastrarCliente(request);
        ClienteResponse response = responseMapper.modelToResponse(cliente);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "atualizar/{id}")
    public @ResponseBody ResponseEntity<ClienteResponse> atualizarCadastro(@PathVariable Long id, @RequestBody ClienteMinRequest minRequest) {
        Cliente clienteAtualizado = clienteService.atualizarCadastroCliente(id, minRequest);
        ClienteResponse response = responseMapper.modelToResponse(clienteAtualizado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<Cliente> desativarOuAtivarCadastro(@PathVariable Long id) {
        Cliente clienteDesativado = clienteService.desativarOuAtivarCliente(id);
        return new ResponseEntity<>(clienteDesativado, HttpStatus.OK);
    }
}
