package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(Long id) {
        super(criarMensagem(id));
    }

    public ClienteNaoEncontradoException(String cpf) {
        super(criarMensagem(cpf));
    }

    private static String criarMensagem(Long id) {
        return "Cliente nao encontrado \nId: " + id;
    }

    private static String criarMensagem(String cpf) {
        return "Cliente nao encontrado \nCpf: " + cpf;
    }
}
