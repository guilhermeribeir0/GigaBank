package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes/")
public class ClienteController {

    private ClienteService clienteService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "cliente/{cpf}")
    public @ResponseBody Cliente buscaPorCpf(@PathVariable String cpf) {
        return clienteService.buscarClientePorCpf(cpf);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "cliente/{nome}")
    public @ResponseBody List<Cliente> buscaPorNome(@PathVariable String nome) {
        return clienteService.buscarClientePorNome(nome);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "cadastrar")
    public @ResponseBody Cliente cadastrar(@RequestBody Cliente cliente) {
        return clienteService.cadastrarCliente(cliente);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "atualizar/{cpf}")
    public @ResponseBody Cliente atualizarCadastro(@PathVariable String cpf, @RequestBody ClienteDTO clienteDTO) {
        return clienteService.atualizarCadastroCliente(cpf, clienteDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{cpf}")
    public @ResponseBody String deletarCadastro(@PathVariable String cpf) {
        return clienteService.deletarCadastroCliente(cpf);

    }
}
