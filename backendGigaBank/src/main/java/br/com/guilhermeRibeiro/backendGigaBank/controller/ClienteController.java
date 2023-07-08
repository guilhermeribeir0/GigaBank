package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes/")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public @ResponseBody ResponseEntity<List<Cliente>> listaTodosOsClientes() {
        return clienteService.listarTodosOsClientes();
    }

    @GetMapping(value = "cpf/{cpf}")
    public @ResponseBody ResponseEntity<Cliente> buscaPorCpf(@PathVariable String cpf) {
        return clienteService.buscarClientePorCpf(cpf);
    }

    @GetMapping(value = "nome/{nome}")
    public @ResponseBody ResponseEntity<List<ClienteDTO>> buscaPorNome(@PathVariable String nome) {
        return clienteService.buscarClientePorNome(nome);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Cliente> cadastrar(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.cadastrarCliente(clienteDTO);
    }

    @PutMapping(value = "atualizar/{cpf}")
    public @ResponseBody ResponseEntity<Cliente> atualizarCadastro(@PathVariable String cpf, @RequestBody ClienteDTO clienteDTO) {
        return clienteService.atualizarCadastroCliente(cpf, clienteDTO);
    }

    @DeleteMapping(value = "/{cpf}")
    public @ResponseBody ResponseEntity<String> deletarCadastro(@PathVariable String cpf) {
        return clienteService.deletarCadastroCliente(cpf);
    }
}
