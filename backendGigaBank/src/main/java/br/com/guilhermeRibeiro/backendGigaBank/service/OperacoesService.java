package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.CompraRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.DepositoRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.SaqueRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.TransferenciaRequest;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cartao;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ContaBancariaRepository;
import br.com.guilhermeRibeiro.backendGigaBank.util.TipoOperacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class OperacoesService {

    @Autowired
    private ContaBancariaService contaBancariaService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ExtratoService extratoService;

    public void depositar(DepositoRequest request, Boolean transferencia) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(request.getAgenciaConta(), request.getNumeroConta());
        if (contaBancaria == null) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        }
        contaBancaria.setSaldo(contaBancaria.getSaldo() + request.getValor());
        contaBancariaService.atualizarSaldo(contaBancaria);

        if (!transferencia) {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.DEPOSITO, request.getValor());
        } else {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.TRANSFERENCIA_RECEBIDA, request.getValor());
        }
    }

    public void sacar(SaqueRequest request, Boolean transferencia, Boolean compra) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(request.getAgenciaConta(), request.getNumeroConta());
        if (contaBancaria == null) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        } else if (contaBancaria.getSaldo() < request.getValor()) {
            throw new RuntimeException(ValidacaoException.SALDO_INSUFICIENTE_EXCEPTION);
        }

        contaBancaria.setSaldo(contaBancaria.getSaldo() - request.getValor());
        contaBancariaService.atualizarSaldo(contaBancaria);

        if (!transferencia && !compra) {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.SAQUE, request.getValor());
        } else if (!compra){
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.TRANSFERENCIA_ENVIADA, request.getValor());
        } else if (!transferencia){
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.COMPRA, request.getValor());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void transferencia(TransferenciaRequest request) {
        SaqueRequest saque = new SaqueRequest(request.getNumeroContaOrigem(), request.getAgenciaOrigem(), request.getValor());
        this.sacar(saque, true, false);
        DepositoRequest deposito = new DepositoRequest(request.getNumeroContaDestino(), request.getAgenciaDestino(), request.getValor());
        this.depositar(deposito, true);
    }

    public void compra(CompraRequest request) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorCliente(request.getCpfCliente());
        if (Objects.isNull(contaBancaria)) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        }
        Cartao cartao = cartaoService.buscarCartaoPorContaBancaria(contaBancaria.getId());
        if (Objects.isNull(cartao)) {
            throw new RuntimeException(ValidacaoException.CARTAO_NAO_VINCULADO_A_CONTA_EXCEPTION);
        }
        SaqueRequest saque = new SaqueRequest(contaBancaria.getNumero(), contaBancaria.getAgencia(), request.getValor());
        this.sacar(saque, false, true);
    }
}
