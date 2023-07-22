package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extrato;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ExtratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
