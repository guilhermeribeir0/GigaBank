package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ExtratoDTO;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extrato;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ExtratoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<ExtratoDTO> buscaExtratoPorPeriodo(String agencia, String numeroConta, String dataInicio, String dataFim) {
        ContaBancaria conta = contaBancariaService.buscarContaPorAgenciaENumero(agencia, numeroConta);
        if (Objects.isNull(conta)) {
            throw new RuntimeException(ValidacaoException.CONTA_NAO_CADASTRADA_EXCEPTION);
        }
        List<Extrato> movimentos = extratoRepository.findByContaBancaria(conta);
        if (Objects.isNull(movimentos)) {
            throw new RuntimeException(ValidacaoException.NAO_EXISTE_MOVIMENTOS_EXCEPTION);
        }

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dataInicial = LocalDateTime.parse(dataInicio + " 00:00:00", formatoData);
        LocalDateTime dataFinal = LocalDateTime.parse(dataFim + " 23:59:59", formatoData);

        List<ExtratoDTO> movimentosDTO = new ArrayList<>();
        for (Extrato extrato: movimentos) {
            if (extrato.getDataOperacao().isAfter(dataInicial) && extrato.getDataOperacao().isBefore(dataFinal)) {
                ExtratoDTO extratoDTO = new ExtratoDTO();
                BeanUtils.copyProperties(extrato, extratoDTO);
                movimentosDTO.add(extratoDTO);
            }
        }
        return movimentosDTO;
    }

}
