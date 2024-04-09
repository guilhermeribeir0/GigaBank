package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class ContaNaoCadastradaException extends RuntimeException {

    public ContaNaoCadastradaException() { }

    public ContaNaoCadastradaException(String agencia, String numeroConta) {
        super(criarMensagem(agencia, numeroConta));
    }

    private static String criarMensagem(String agencia, String numeroConta) {
        return "Conta não cadastrada \nAgenca: "+agencia+"\nNumero Conta: "+numeroConta;
    }
}
