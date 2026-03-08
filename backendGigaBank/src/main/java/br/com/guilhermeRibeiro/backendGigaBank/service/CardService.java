package br.com.guilhermeRibeiro.backendGigaBank.service;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Card;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.exception.CartaoInvalidoException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.CartaoNaoVinculadoContaException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.CardRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card cadastrar(Account account) {
            Card card = criarCartao();
            card.setAccount(account);
            cardRepository.save(card);

            return card;
    }

    public Card buscarCartaoPorNumeroCvvDataVencimento(String numero, String cvv, LocalDate dataVencimento) {
        Optional<Card> cartao = cardRepository.findByNumberAndCvvAndExpirationDate(numero, cvv, dataVencimento);
        if (cartao.isEmpty()) {
            throw new CartaoInvalidoException();
        }
        return cartao.get();
    }

    public Card buscarCartaoPorContaBancaria(Long idConta) {
        Optional<Card> cartao = cardRepository.findByAccountId(idConta);
        if (cartao.isEmpty()) {
            throw new CartaoNaoVinculadoContaException(idConta);
        }
        return cartao.get();
    }

    private Card criarCartao() {
        Card card = new Card();
        LocalDate dataVencimento = LocalDate.now().plusYears(4);

        card.setNumber(gerarNumeroCartao());
        card.setCvv(gerarCvv());
        card.setExpirationDate(dataVencimento);
        card.setActive(true);

        return card;
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
        } while (cardRepository.findByNumber(numero).isEmpty());

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
        } while (cardRepository.findByCvv(cvv).isEmpty());

        return cvv;
    }
}
