package br.com.guilhermeRibeiro.backendGigaBank.exception;

public class ValidacaoException {

    public static final String CLIENTE_NAO_CADASTRADO_EXCEPTION = "Cliente não cadastrado na base de dados";
    public static final String CONTA_NAO_CADASTRADA_EXCEPTION = "Conta não cadastrada na base de dados";
    public static final String CPF_INVALIDO_EXCEPTION = "CPF inválido: ";
    public static final String NOME_INVALIDO_EXCEPTION = "Nome informado é inválido";
    public static final String CLIENTE_SEM_CONTA_VINCULADA_EXCEPTION = "Cliente não possui conta vinculada";
    public static final String SALDO_INSUFICIENTE_EXCEPTION = "Saldo insuficiente";
    public static final String NAO_EXISTE_MOVIMENTOS_EXCEPTION = "Nenhum registro encontrado";
    public static final String CARTAO_NAO_VINCULADO_A_CONTA_EXCEPTION = "Cartão não está vinculado a essa conta";
    public static final String CARTAO_INVALIDO_EXCEPTION = "Cartão Inválido";
}
