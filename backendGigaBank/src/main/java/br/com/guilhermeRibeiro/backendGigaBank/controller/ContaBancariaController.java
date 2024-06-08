package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.ContaBancariaRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.contaBancaria.ContaBancariaResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.mapper.contaBancaria.ContaBancariaResponseMapper;
import br.com.guilhermeRibeiro.backendGigaBank.service.ContaBancariaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaBancariaController {

    private final ContaBancariaService contaBancariaService;
    private final ContaBancariaResponseMapper responseMapper;

    public ContaBancariaController(ContaBancariaService contaBancariaService,
                                   ContaBancariaResponseMapper contaBancariaResponseMapper) {
        this.contaBancariaService = contaBancariaService;
        this.responseMapper = contaBancariaResponseMapper;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<ContaBancaria>> listaTodasAsContas() {
        List<ContaBancaria> listaDeContas = contaBancariaService.listaTodasAsContas();
        return new ResponseEntity<>(listaDeContas, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastro")
    public @ResponseBody ResponseEntity<ContaBancariaResponse> cadastrarContaBancaria(@RequestBody ContaBancariaRequest request) {
        ContaBancaria contaBancaria = contaBancariaService.cadastrarContaBancaria(request);
        ContaBancariaResponse response = responseMapper.modelToResponse(contaBancaria);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
