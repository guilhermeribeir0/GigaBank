package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.*;
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

    @PostMapping(value = "/saque")
    public @ResponseBody ResponseEntity<Void> sacar(@RequestBody SaqueDTO saqueDTO) {
        operacoesService.sacar(saqueDTO, false, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/deposito")
    public @ResponseBody ResponseEntity<Void> depositar(@RequestBody DepositoDTO depositoDTO) {
        operacoesService.depositar(depositoDTO, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/transferencia")
    public @ResponseBody ResponseEntity<Void> transferir(@RequestBody TransferenciaDTO transferenciaDTO) {
        operacoesService.transferencia(transferenciaDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/compra")
    public @ResponseBody ResponseEntity<Void> comprar(@RequestBody CompraDTO compraDTO) {
        operacoesService.compra(compraDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/extrato/{agencia}/{numeroConta}/{dataInicio}/{dataFim}")
    public @ResponseBody ResponseEntity<List<ExtratoDTO>> consultarExtrato (@PathVariable String agencia,
                                                                            @PathVariable String numeroConta,
                                                                            @PathVariable String dataInicio,
                                                                            @PathVariable String dataFim) {
        List<ExtratoDTO> movimentos = extratoService.buscaExtratoPorPeriodo(agencia, numeroConta, dataInicio, dataFim);
        return new ResponseEntity<>(movimentos, HttpStatus.OK);
    }
}
