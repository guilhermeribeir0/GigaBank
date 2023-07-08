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
        List<Cliente> listaDeClientes = clienteService.listarTodosOsClientes();
        return new ResponseEntity<>(listaDeClientes, HttpStatus.OK);
    }

    @GetMapping(value = "cpf/{cpf}")
    public @ResponseBody ResponseEntity<Cliente> buscaPorCpf(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping(value = "nome/{nome}")
    public @ResponseBody ResponseEntity<List<ClienteDTO>> buscaPorNome(@PathVariable String nome) {
        List<ClienteDTO> clientes = clienteService.buscarClientePorNome(nome);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Cliente> cadastrar(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.cadastrarCliente(clienteDTO);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @PutMapping(value = "atualizar/{cpf}")
    public @ResponseBody ResponseEntity<Cliente> atualizarCadastro(@PathVariable String cpf, @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteAtualizado = clienteService.atualizarCadastroCliente(cpf, clienteDTO);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{cpf}")
    public @ResponseBody ResponseEntity<Void> deletarCadastro(@PathVariable String cpf) {
        clienteService.deletarCadastroCliente(cpf);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
