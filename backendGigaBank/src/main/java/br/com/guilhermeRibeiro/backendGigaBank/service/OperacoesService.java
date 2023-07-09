package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.DepositoDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.SaqueDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.TransferenciaDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperacoesService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private ContaBancariaService contaBancariaService;

    public void depositar(DepositoDTO depositoDTO, Boolean transferencia) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(depositoDTO.getAgenciaConta(), depositoDTO.getNumeroConta());

        if (contaBancaria == null) {
            throw new ValidacaoException("Operação inválida");
        }

        contaBancaria.setSaldo(contaBancaria.getSaldo() + depositoDTO.getValor());
        contaBancariaRepository.save(contaBancaria);
    }

    public void sacar(SaqueDTO saqueDTO, Boolean transferencia) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(saqueDTO.getAgenciaConta(), saqueDTO.getNumeroConta());

        if (contaBancaria == null) {
            throw new ValidacaoException("Operação inválida");
        } else if (contaBancaria.getSaldo() < saqueDTO.getValor()) {
            throw new ValidacaoException("Saldo insuficiente");
        }

        contaBancaria.setSaldo(contaBancaria.getSaldo() - saqueDTO.getValor());
        contaBancariaRepository.save(contaBancaria);
    }

    @Transactional(rollbackFor = Exception.class)
    public void transferencia(TransferenciaDTO transferenciaDTO) {

        SaqueDTO saqueDTO = new SaqueDTO();
        saqueDTO.setAgenciaConta(transferenciaDTO.getAgenciaOrigem());
        saqueDTO.setNumeroConta(transferenciaDTO.getNumeroContaOrigem());
        saqueDTO.setValor(transferenciaDTO.getValor());
        this.sacar(saqueDTO, true);

        DepositoDTO depositoDTO = new DepositoDTO();
        depositoDTO.setAgenciaConta(transferenciaDTO.getAgenciaDestino());
        depositoDTO.setNumeroConta(transferenciaDTO.getNumeroContaDestino());
        depositoDTO.setValor(transferenciaDTO.getValor());
        this.depositar(depositoDTO, true);
    }
}
