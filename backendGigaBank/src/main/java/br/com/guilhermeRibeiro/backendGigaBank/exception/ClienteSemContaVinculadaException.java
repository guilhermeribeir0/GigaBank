package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class ClienteSemContaVinculadaException extends RuntimeException {

    public ClienteSemContaVinculadaException() {
        super("Cliente não possui conta vinculada");
    }
}
