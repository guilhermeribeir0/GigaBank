package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.CompraDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.DepositoDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.SaqueDTO;
import br.com.guilhermeRibeiro.backendGigaBank.dto.TransferenciaDTO;
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
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private ContaBancariaService contaBancariaService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ExtratoService extratoService;

    public void depositar(DepositoDTO depositoDTO, Boolean transferencia) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(depositoDTO.getAgenciaConta(), depositoDTO.getNumeroConta());
        if (contaBancaria == null) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        }
        contaBancaria.setSaldo(contaBancaria.getSaldo() + depositoDTO.getValor());
        contaBancariaRepository.save(contaBancaria);

        if (!transferencia) {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.DEPOSITO, depositoDTO.getValor());
        } else {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.TRANSFERENCIA_RECEBIDA, depositoDTO.getValor());
        }
    }

    public void sacar(SaqueDTO saqueDTO, Boolean transferencia, Boolean compra) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorAgenciaENumero(saqueDTO.getAgenciaConta(), saqueDTO.getNumeroConta());
        if (contaBancaria == null) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        } else if (contaBancaria.getSaldo() < saqueDTO.getValor()) {
            throw new RuntimeException(ValidacaoException.SALDO_INSUFICIENTE_EXCEPTION);
        }

        contaBancaria.setSaldo(contaBancaria.getSaldo() - saqueDTO.getValor());
        contaBancariaRepository.save(contaBancaria);

        if (!transferencia && !compra) {
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.SAQUE, saqueDTO.getValor());
        } else if (!compra){
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.TRANSFERENCIA_ENVIADA, saqueDTO.getValor());
        } else if (!transferencia){
            extratoService.gerarExtrato(contaBancaria, TipoOperacaoUtil.COMPRA, saqueDTO.getValor());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void transferencia(TransferenciaDTO transferenciaDTO) {
        SaqueDTO saqueDTO = new SaqueDTO(transferenciaDTO.getNumeroContaOrigem(), transferenciaDTO.getAgenciaOrigem(), transferenciaDTO.getValor());
        this.sacar(saqueDTO, true, false);
        DepositoDTO depositoDTO = new DepositoDTO(transferenciaDTO.getNumeroContaDestino(), transferenciaDTO.getAgenciaDestino(), transferenciaDTO.getValor());
        this.depositar(depositoDTO, true);
    }

    public void compra(CompraDTO compraDTO) {
        ContaBancaria contaBancaria = contaBancariaService.buscarContaPorCliente(compraDTO.getCpfCliente());
        if (Objects.isNull(contaBancaria)) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        }
        Cartao cartao = cartaoService.buscarCartaoPorContaBancaria(contaBancaria.getId());
        if (Objects.isNull(cartao)) {
            throw new RuntimeException(ValidacaoException.CARTAO_NAO_VINCULADO_A_CONTA_EXCEPTION);
        }
        SaqueDTO saqueDTO = new SaqueDTO(contaBancaria.getNumero(), contaBancaria.getAgencia(), compraDTO.getValor());
        this.sacar(saqueDTO, false, true);
    }
}
