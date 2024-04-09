package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.CompraRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.DepositoRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.SaqueRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.TransferenciaRequest;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cartao;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.CartaoNaoVinculadoContaException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ContaNaoCadastradaException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.SaldoInsuficienteException;
import br.com.guilhermeRibeiro.backendGigaBank.util.TipoOperacaoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class OperacoesService {

    private final ContaBancariaService contaBancariaService;
    private final CartaoService cartaoService;
    private final ExtratoService extratoService;

    public OperacoesService(
            ContaBancariaService contaBancariaService,
            CartaoService cartaoService,
            ExtratoService extratoService
    ) {
        this.contaBancariaService = contaBancariaService;
        this.cartaoService = cartaoService;
        this.extratoService = extratoService;
    }

    public void depositar(DepositoRequest request, Boolean transferencia) {
        String agencia = request.getAgenciaConta();
        String numeroConta = request.getNumeroConta();

        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(agencia, numeroConta);
        if (contaBancaria == null) {
            throw new ContaNaoCadastradaException(agencia, numeroConta);
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
        String agencia = request.getAgenciaConta();
        String numeroConta = request.getNumeroConta();

        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(agencia, numeroConta);
        if (contaBancaria == null) {
            throw new ContaNaoCadastradaException(agencia, numeroConta);
        } else if (contaBancaria.getSaldo() < request.getValor()) {
            throw new SaldoInsuficienteException();
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
            throw new ContaNaoCadastradaException();
        }
        Cartao cartao = cartaoService.buscarCartaoPorContaBancaria(contaBancaria.getId());
        if (Objects.isNull(cartao)) {
            throw new CartaoNaoVinculadoContaException(contaBancaria.getId());
        }
        SaqueRequest saque = new SaqueRequest(contaBancaria.getNumero(), contaBancaria.getAgencia(), request.getValor());
        this.sacar(saque, false, true);
    }
}
