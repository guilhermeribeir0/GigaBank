package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.CompraRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.DepositoRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.SaqueRequest;
import br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes.TransferenciaRequest;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Card;
import br.com.guilhermeRibeiro.backendGigaBank.exception.CartaoNaoVinculadoContaException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ContaNaoCadastradaException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.SaldoInsuficienteException;
import br.com.guilhermeRibeiro.backendGigaBank.util.TipoOperacaoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class OperacoesService {

    private final ContaBancariaService contaBancariaService;
    private final CardService cardService;
    private final ExtratoService extratoService;

    public OperacoesService(
            ContaBancariaService contaBancariaService,
            CardService cardService,
            ExtratoService extratoService
    ) {
        this.contaBancariaService = contaBancariaService;
        this.cardService = cardService;
        this.extratoService = extratoService;
    }

    @Transactional
    public void depositar(DepositoRequest request, Boolean transferencia) {
        String agencia = request.getAgenciaConta();
        String numeroConta = request.getNumeroConta();
        BigDecimal valor = BigDecimal.valueOf(request.getValor());

        Account account = validarCadastroConta(agencia, numeroConta);

        account.setBalance(account.getBalance().add(valor));
        contaBancariaService.atualizarSaldo(account);

        if (!transferencia) {
            extratoService.gerarExtrato(account, TipoOperacaoUtil.DEPOSITO, valor);
        } else {
            extratoService.gerarExtrato(account, TipoOperacaoUtil.TRANSFERENCIA_RECEBIDA, valor);
        }
    }

    @Transactional
    public void sacar(SaqueRequest request, Boolean transferencia, Boolean compra) {
        String agencia = request.getAgenciaConta();
        String numeroConta = request.getNumeroConta();
        BigDecimal valor = BigDecimal.valueOf(request.getValor());

        Account account = validarCadastroConta(agencia, numeroConta);
        validarSaldo(account.getBalance(), valor);

        account.setBalance(account.getBalance().subtract(valor));
        contaBancariaService.atualizarSaldo(account);

        if (!transferencia && !compra) {
            extratoService.gerarExtrato(account, TipoOperacaoUtil.SAQUE, valor);
        } else if (!compra){
            extratoService.gerarExtrato(account, TipoOperacaoUtil.TRANSFERENCIA_ENVIADA, valor);
        } else if (!transferencia){
            extratoService.gerarExtrato(account, TipoOperacaoUtil.COMPRA, valor);
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
        try {
            Account account = buscarContaBancariaPorCliente(request.getCpfCliente());
            Card card = buscarCartaoPorContaBancaria(account.getId());

            SaqueRequest saque = new SaqueRequest(account.getNumber(), account.getAgency(), request.getValor());
            this.sacar(saque, false, true);
        } catch (ContaNaoCadastradaException | CartaoNaoVinculadoContaException exception) {
            String.format("Erro ao realizar operacao. %s", exception.getMessage());
        }
    }

    private Account validarCadastroConta(String agencia, String numeroConta) {
        Account account = contaBancariaService.buscarContaPorAgenciaENumero(agencia, numeroConta);

        if (account == null) {
            throw new ContaNaoCadastradaException(agencia, numeroConta);
        }

        return account;
    }

    private void validarSaldo(BigDecimal saldo, BigDecimal valor) {
        if (saldo.doubleValue() < valor.doubleValue()) {
            throw new SaldoInsuficienteException();
        }
    }

    private Account buscarContaBancariaPorCliente(String cpf) {
        Account account = contaBancariaService.buscarContaPorCliente(cpf);
        if (Objects.isNull(account)) {
            throw new ContaNaoCadastradaException();
        }

        return account;
    }

    private Card buscarCartaoPorContaBancaria(Long idConta) {
        Card card = cardService.buscarCartaoPorContaBancaria(idConta);
        if (Objects.isNull(card)) {
            throw new CartaoNaoVinculadoContaException(idConta);
        }

        return card;
    }
}
