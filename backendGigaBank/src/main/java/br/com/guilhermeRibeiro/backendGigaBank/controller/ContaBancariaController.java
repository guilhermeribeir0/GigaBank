package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.service.ContaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/contas/")
public class ContaBancariaController {

    @Autowired
    private ContaBancariaService contaBancariaService;

    @GetMapping
    public @ResponseBody ResponseEntity<List<ContaBancaria>> listaTodasAsContas() {
        List<ContaBancaria> listaDeContas = contaBancariaService.listaTodasAsContas();
        return new ResponseEntity<>(listaDeContas, HttpStatus.OK);
    }
}
