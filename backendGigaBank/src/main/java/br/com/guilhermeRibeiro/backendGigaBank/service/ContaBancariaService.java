package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.DepositoDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.SaqueDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.TransferenciaDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.zip.DeflaterOutputStream;

@Service
public class ContaBancariaService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    public List<ContaBancaria> listaTodasAsContas() {
        List<ContaBancaria> listaContas = contaBancariaRepository.findAll();
        return listaContas;
    }

    public void depositar(DepositoDTO depositoDTO, Boolean transferencia) {
        ContaBancaria contaBancaria = this.buscarContaPorAgenciaENumero(depositoDTO.getAgenciaConta(), depositoDTO.getNumeroConta());

        if (contaBancaria == null) {
            throw new ValidacaoException("Operação inválida");
        }

        contaBancaria.setSaldo(contaBancaria.getSaldo() + depositoDTO.getValor());
        contaBancariaRepository.save(contaBancaria);
    }

    public void sacar(SaqueDTO saqueDTO, Boolean transferencia) {
        ContaBancaria contaBancaria = this.buscarContaPorAgenciaENumero(saqueDTO.getAgenciaConta(), saqueDTO.getNumeroConta());

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

    public ContaBancaria buscarContaPorAgenciaENumero(String agencia, String numeroConta) {
        ContaBancaria contaBancaria = contaBancariaRepository.findByAgenciaAndNumero(agencia, numeroConta);

        if (contaBancaria == null) {
            throw new ValidacaoException("Conta bancária não encontrada");
        }

        return contaBancaria;
    }
}
