package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteMinDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping(value = "atualizar/{id}")
    public @ResponseBody ResponseEntity<Cliente> atualizarCadastro(@PathVariable Long id, @RequestBody ClienteMinDTO clienteMinDTO) {
        Cliente clienteAtualizado = clienteService.atualizarCadastroCliente(id, clienteMinDTO);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<Cliente> desativarOuAtivarCadastro(@PathVariable Long id) {
        Cliente clienteDesativado = clienteService.desativarOuAtivarCliente(id);
        return new ResponseEntity<>(clienteDesativado, HttpStatus.OK);
    }
}
