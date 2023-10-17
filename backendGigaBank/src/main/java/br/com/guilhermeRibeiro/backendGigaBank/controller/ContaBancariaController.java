package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.ContaBancariaRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.contaBancaria.ContaBancariaResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.service.ContaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaBancariaController {

    @Autowired
    private ContaBancariaService contaBancariaService;

    @GetMapping
    public @ResponseBody ResponseEntity<List<ContaBancaria>> listaTodasAsContas() {
        List<ContaBancaria> listaDeContas = contaBancariaService.listaTodasAsContas();
        return new ResponseEntity<>(listaDeContas, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastro")
    public @ResponseBody ResponseEntity<ContaBancariaResponse> cadastrarContaBancaria(@RequestBody ContaBancariaRequest request) {
        ContaBancariaResponse response = contaBancariaService.cadastrarContaBancaria(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
