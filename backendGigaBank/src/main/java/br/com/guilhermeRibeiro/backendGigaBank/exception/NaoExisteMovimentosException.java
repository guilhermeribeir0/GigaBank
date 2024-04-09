package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class NaoExisteMovimentosException extends RuntimeException {

    public NaoExisteMovimentosException() {
        super("Nenhum registro encontrado");
    }
}
