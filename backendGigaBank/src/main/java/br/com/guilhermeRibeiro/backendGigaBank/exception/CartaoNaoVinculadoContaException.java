package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class CartaoNaoVinculadoContaException extends RuntimeException {

    public CartaoNaoVinculadoContaException(Long idConta) {
        super(criarMensagem(idConta));
    }

    private static String criarMensagem(Long idConta) {
        return "Cartão não está vinculado a essa conta \nID Conta: " + idConta;
    }
}
