package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.response.operacoes.ExtratoResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extrato;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ExtratoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExtratoService {

    @Autowired
    private ExtratoRepository extratoRepository;

    @Autowired
    private ContaBancariaService contaBancariaService;

    public void gerarExtrato(ContaBancaria conta, String tipoOperacao, Double valor) {
        Extrato extrato = new Extrato();
        LocalDateTime dataOperacao = LocalDateTime.now();
        extrato.setContaBancaria(conta);
        extrato.setTipoOperacao(tipoOperacao);
        extrato.setValor(valor);
        extrato.setDataOperacao(dataOperacao);
        extratoRepository.save(extrato);
    }

    public List<Extrato> buscaExtratoPorPeriodo(String agencia, String numeroConta, String dataInicio, String dataFim) {
        ContaBancaria conta = contaBancariaService.buscarContaPorAgenciaENumero(agencia, numeroConta);
        if (Objects.isNull(conta)) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        }
        List<Extrato> movimentos = extratoRepository.findByContaBancaria(conta);
        if (Objects.isNull(movimentos) || movimentos.isEmpty()) {
            throw new RuntimeException(ValidacaoException.NAO_EXISTE_MOVIMENTOS_EXCEPTION);
        }

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dataInicial = LocalDateTime.parse(dataInicio + " 00:00:00", formatoData);
        LocalDateTime dataFinal = LocalDateTime.parse(dataFim + " 23:59:59", formatoData);

        return movimentos.stream().filter(movimento -> movimento.getDataOperacao().isAfter(dataInicial)
                        && movimento.getDataOperacao().isBefore(dataFinal))
                .collect(Collectors.toList());
    }

}
