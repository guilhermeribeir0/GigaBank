package br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes;

import java.io.Serializable;

public class SaqueRequest implements Serializable {

    private static final Long serialVersionUID = 1L;
    private String numeroConta;
    private String agenciaConta;
    private Double valor;

    public SaqueRequest() {
    }

    public SaqueRequest(String numeroConta, String agenciaConta, Double valor) {
        this.numeroConta = numeroConta;
        this.agenciaConta = agenciaConta;
        this.valor = valor;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgenciaConta() {
        return agenciaConta;
    }

    public void setAgenciaConta(String agenciaConta) {
        this.agenciaConta = agenciaConta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
