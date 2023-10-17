package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.*;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.operacoes.ExtratoResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extrato;
import br.com.guilhermeRibeiro.backendGigaBank.mapper.operacoes.ExtratoResponseMapper;
import br.com.guilhermeRibeiro.backendGigaBank.service.ExtratoService;
import br.com.guilhermeRibeiro.backendGigaBank.service.OperacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/operacoes")
public class OperacoesController {

    @Autowired
    private OperacoesService operacoesService;

    @Autowired
    private ExtratoService extratoService;

    @Autowired
    private ExtratoResponseMapper responseMapper;

    @PostMapping(value = "/saque")
    public @ResponseBody ResponseEntity<Void> sacar(@RequestBody SaqueRequest request) {
        operacoesService.sacar(request, false, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/deposito")
    public @ResponseBody ResponseEntity<Void> depositar(@RequestBody DepositoRequest request) {
        operacoesService.depositar(request, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/transferencia")
    public @ResponseBody ResponseEntity<Void> transferir(@RequestBody TransferenciaRequest request) {
        operacoesService.transferencia(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/compra")
    public @ResponseBody ResponseEntity<Void> comprar(@RequestBody CompraRequest request) {
        operacoesService.compra(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/extrato/{agencia}/{numeroConta}/{dataInicio}/{dataFim}")
    public @ResponseBody ResponseEntity<List<ExtratoResponse>> consultarExtrato (@PathVariable String agencia,
                                                                                 @PathVariable String numeroConta,
                                                                                 @PathVariable String dataInicio,
                                                                                 @PathVariable String dataFim) {
        List<Extrato> movimentos = extratoService.buscaExtratoPorPeriodo(agencia, numeroConta, dataInicio, dataFim);
        List<ExtratoResponse> movimentosResponse = responseMapper.modelListToResponseList(movimentos);
        return new ResponseEntity<>(movimentosResponse, HttpStatus.OK);
    }
}
