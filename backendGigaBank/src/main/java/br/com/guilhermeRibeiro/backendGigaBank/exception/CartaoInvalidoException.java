package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class CartaoInvalidoException extends RuntimeException {

    public CartaoInvalidoException() {
        super("Cartão Inválido");
    }
}
