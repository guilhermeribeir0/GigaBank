package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cartao;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import br.com.guilhermeRibeiro.backendGigaBank.exception.CartaoInvalidoException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.CartaoNaoVinculadoContaException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.CartaoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Cartao cadastrar(ContaBancaria contaBancaria) {
            Cartao cartao = criarCartao();
            cartao.setContaBancaria(contaBancaria);
            cartaoRepository.save(cartao);

            return cartao;
    }

    public Cartao buscarCartaoPorNumeroCvvDataVencimento(String numero, String cvv, LocalDate dataVencimento) {
        Optional<Cartao> cartao = cartaoRepository.findByNumeroAndCvvAndDataVencimento(numero, cvv, dataVencimento);
        if (cartao.isEmpty()) {
            throw new CartaoInvalidoException();
        }
        return cartao.get();
    }

    public Cartao buscarCartaoPorContaBancaria(Long idConta) {
        Optional<Cartao> cartao = cartaoRepository.findByContaBancariaId(idConta);
        if (cartao.isEmpty()) {
            throw new CartaoNaoVinculadoContaException(idConta);
        }
        return cartao.get();
    }

    private Cartao criarCartao() {
        Cartao cartao = new Cartao();
        LocalDate dataVencimento = LocalDate.now().plusYears(4);

        cartao.setNumero(gerarNumeroCartao());
        cartao.setCvv(gerarCvv());
        cartao.setDataVencimento(dataVencimento);
        cartao.setAtivo(true);

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
        } while (cartaoRepository.findByNumero(numero).isEmpty());

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
        } while (cartaoRepository.findByCvv(cvv).isEmpty());

        return cvv;
    }
}
