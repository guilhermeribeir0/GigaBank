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

import java.math.BigDecimal;
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

    @Transactional
    public void depositar(DepositoRequest request, Boolean transferencia) {
        String agencia = request.getAgenciaConta();
        String numeroConta = request.getNumeroConta();
        BigDecimal valor = BigDecimal.valueOf(request.getValor());

        ContaBancaria contaBancaria = validarCadastroConta(agencia, numeroConta);

        contaBancaria.setSaldo(contaBancaria.getSaldo().add(valor));
        contaBancariaService.atualizarSaldo(contaBancaria);

        if (!transferencia) {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.DEPOSITO, valor);
        } else {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.TRANSFERENCIA_RECEBIDA, valor);
        }
    }

    @Transactional
    public void sacar(SaqueRequest request, Boolean transferencia, Boolean compra) {
        String agencia = request.getAgenciaConta();
        String numeroConta = request.getNumeroConta();
        BigDecimal valor = BigDecimal.valueOf(request.getValor());

        ContaBancaria contaBancaria = validarCadastroConta(agencia, numeroConta);
        validarSaldo(contaBancaria.getSaldo(), valor);

        contaBancaria.setSaldo(contaBancaria.getSaldo().subtract(valor));
        contaBancariaService.atualizarSaldo(contaBancaria);

        if (!transferencia && !compra) {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.SAQUE, valor);
        } else if (!compra){
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.TRANSFERENCIA_ENVIADA, valor);
        } else if (!transferencia){
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.COMPRA, valor);
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
            ContaBancaria contaBancaria = buscarContaBancariaPorCliente(request.getCpfCliente());
            Cartao cartao = buscarCartaoPorContaBancaria(contaBancaria.getId());

            SaqueRequest saque = new SaqueRequest(contaBancaria.getNumero(), contaBancaria.getAgencia(), request.getValor());
            this.sacar(saque, false, true);
        } catch (ContaNaoCadastradaException | CartaoNaoVinculadoContaException exception) {
            String.format("Erro ao realizar operacao. %s", exception.getMessage());
        }
    }

    private ContaBancaria validarCadastroConta(String agencia, String numeroConta) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(agencia, numeroConta);

        if (contaBancaria == null) {
            throw new ContaNaoCadastradaException(agencia, numeroConta);
        }

        return contaBancaria;
    }

    private void validarSaldo(BigDecimal saldo, BigDecimal valor) {
        if (saldo.doubleValue() < valor.doubleValue()) {
            throw new SaldoInsuficienteException();
        }
    }

    private ContaBancaria buscarContaBancariaPorCliente(String cpf) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorCliente(cpf);
        if (Objects.isNull(contaBancaria)) {
            throw new ContaNaoCadastradaException();
        }

        return contaBancaria;
    }

    private Cartao buscarCartaoPorContaBancaria(Long idConta) {
        Cartao cartao = cartaoService.buscarCartaoPorContaBancaria(idConta);
        if (Objects.isNull(cartao)) {
            throw new CartaoNaoVinculadoContaException(idConta);
        }

        return cartao;
    }
}
