package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cartao;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ValidacaoException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.CartaoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao cadastrar(ContaBancaria contaBancaria) {
            Cartao cartao = new Cartao();
            LocalDate dataVencimento = LocalDate.now().plusYears(4);
            cartao.setNumero(gerarNumeroCartao());
            cartao.setCvv(gerarCvv());
            cartao.setContaBancaria(contaBancaria);
            cartao.setDataVencimento(dataVencimento);
            cartao.setAtivo(true);
            cartaoRepository.save(cartao);
            return cartao;
    }

    public Cartao buscarCartaoPorNumeroCvvDataVencimento(String numero, String cvv, LocalDate dataVencimento) {
        Cartao cartao = cartaoRepository.findByNumeroAndCvvAndDataVencimento(numero, cvv, dataVencimento);
        if (Objects.isNull(cartao)) {
            throw new RuntimeException(ValidacaoException.CARTAO_INVALIDO_EXCEPTION);
        }
        return cartao;
    }

    public Cartao buscarCartaoPorContaBancaria(Long idConta) {
        Cartao cartao = cartaoRepository.findByContaBancariaId(idConta);
        if (Objects.isNull(cartao)) {
            throw new RuntimeException(ValidacaoException.CARTAO_NAO_VINCULADO_A_CONTA_EXCEPTION);
        }
        return cartao;
    }

    private String gerarNumeroCartao() {

        String numero = "";
        int min = 1;
        int max = 9999;
        int range = max - min + 1;

        do {
            for (int i = 0; i < 4; i++) {
                int rand = (int)(Math.random() * range) + min;
                String num = Integer.toString(rand);
                StringUtils.leftPad(num, 4, "0");
                numero = numero.concat(num);
            }
        } while (cartaoRepository.findByNumero(numero) != null);

        return numero;
    }

    private String gerarCvv() {

        String cvv = "";
        int min = 1;
        int max = 999;
        int range = max - min + 1;

        do {
            int rand = (int)(Math.random() * range) + min;
            String num = Integer.toString(rand);
            StringUtils.leftPad(num, 4, "0");
            cvv = cvv.concat(num);
        } while (cartaoRepository.findByCvv(cvv) != null);

        return cvv;
    }
}
