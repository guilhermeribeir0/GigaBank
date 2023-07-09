package br.com.guilhermeRibeiro.backendGigaBank.controller;

import br.com.guilhermeRibeiro.backendGigaBank.dto.DepositoDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.SaqueDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.TransferenciaDTO;
import br.com.guilhermeRibeiro.backendGigaBank.service.OperacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/operacoes")
public class OperacoesController {

    @Autowired
    private OperacoesService operacoesService;

    @PostMapping(value = "/saque")
    public @ResponseBody ResponseEntity<Void> sacar(@RequestBody SaqueDTO saqueDTO) {
        operacoesService.sacar(saqueDTO, false);
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
}
