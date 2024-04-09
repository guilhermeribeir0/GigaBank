package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class CpfInvalidoException extends RuntimeException {

    public CpfInvalidoException(String cpf) {
        super(criarMensagem(cpf));
    }

    private static String criarMensagem(String cpf) {
        return "CPF inválido: " + cpf;
    }
}
