package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente");
    }
}
