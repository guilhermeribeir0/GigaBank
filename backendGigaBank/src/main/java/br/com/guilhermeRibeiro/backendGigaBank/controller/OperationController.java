package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.extract.ExtractRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.*;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.operacoes.ExtractResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extract;
import br.com.guilhermeRibeiro.backendGigaBank.mapper.operacoes.ExtractResponseMapper;
import br.com.guilhermeRibeiro.backendGigaBank.service.ExtractService;
import br.com.guilhermeRibeiro.backendGigaBank.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/operations")
public class OperationController {

    private final OperationService operationService;
    private final ExtractService extractService;
    private final ExtractResponseMapper responseMapper;

    public OperationController(
            OperationService operationService,
            ExtractService extractService,
            ExtractResponseMapper responseMapper
    ) {
        this.operationService = operationService;
        this.extractService = extractService;
        this.responseMapper = responseMapper;
    }

    @PostMapping(value = "/saque")
    public @ResponseBody ResponseEntity<Void> sacar(@RequestBody SaqueRequest request) {
        operationService.sacar(request, false, false);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/deposito")
    public @ResponseBody ResponseEntity<Void> depositar(@RequestBody DepositoRequest request) {
        operationService.depositar(request, false);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/transferencia")
    public @ResponseBody ResponseEntity<Void> transferir(@RequestBody TransferenciaRequest request) {
        operationService.transferencia(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/compra")
    public @ResponseBody ResponseEntity<Void> comprar(@RequestBody CompraRequest request) {
        operationService.compra(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/extract/{agency}/{accountNumber}/{startDate}/{endDate}")
    public @ResponseBody ResponseEntity<List<ExtractResponse>> consultExtract(@RequestBody ExtractRequest request) {
        List<Extract> movements = extractService.findExtractByPeriod(request);
        List<ExtractResponse> movementsResponse = responseMapper.modelListToResponseList(movements);
        return new ResponseEntity<>(movementsResponse, HttpStatus.OK);
    }
}
