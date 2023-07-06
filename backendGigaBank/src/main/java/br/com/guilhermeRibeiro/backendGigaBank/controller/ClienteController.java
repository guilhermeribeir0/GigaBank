package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ClienteDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import br.com.guilhermeRibeiro.backendGigaBank.exception.RegraDeNegocioException;
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
        try {
            return clienteService.buscarClientePorCpf(cpf);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao executar a requisição. " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "cliente/{nome}")
    public @ResponseBody List<Cliente> buscaPorNome(@PathVariable String nome) {
        try {
            return clienteService.buscarClientePorNome(nome);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao executar a requisição. " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "cadastrar")
    public @ResponseBody Cliente cadastrar(@RequestBody Cliente cliente) {
        try {
            return clienteService.cadastrarCliente(cliente);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao executar a requisição. " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "atualizar/{cpf}")
    public @ResponseBody Cliente atualizarCadastro(@PathVariable String cpf, @RequestBody ClienteDTO clienteDTO) {
        try {
            return clienteService.atualizarCadastroCliente(cpf, clienteDTO);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao executar a requisição. " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{cpf}")
    public @ResponseBody String deletarCadastro(@PathVariable String cpf) {
        try {
            return clienteService.deletarCadastroCliente(cpf);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao executar a requisição. " + e.getMessage());
        }
    }
}
