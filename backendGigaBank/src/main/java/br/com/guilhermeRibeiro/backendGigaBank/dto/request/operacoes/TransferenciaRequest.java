package br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes;

import java.io.Serializable;

public class TransferenciaRequest implements Serializable {

    private static final Long serialVersionUID = 1L;
    private String agenciaOrigem;
    private String numeroContaOrigem;
    private String agenciaDestino;
    private String numeroContaDestino;
    private Double valor;

    public TransferenciaRequest() {
    }

    public TransferenciaRequest(String agenciaOrigem, String numeroContaOrigem, String agenciaDestino, String numeroContaDestino, Double valor) {
        this.agenciaOrigem = agenciaOrigem;
        this.numeroContaOrigem = numeroContaOrigem;
        this.agenciaDestino = agenciaDestino;
        this.numeroContaDestino = numeroContaDestino;
        this.valor = valor;
    }

    public String getAgenciaOrigem() {
        return agenciaOrigem;
    }

    public void setAgenciaOrigem(String agenciaOrigem) {
        this.agenciaOrigem = agenciaOrigem;
    }

    public String getNumeroContaOrigem() {
        return numeroContaOrigem;
    }

    public void setNumeroContaOrigem(String numeroContaOrigem) {
        this.numeroContaOrigem = numeroContaOrigem;
    }

    public String getAgenciaDestino() {
        return agenciaDestino;
    }

    public void setAgenciaDestino(String agenciaDestino) {
        this.agenciaDestino = agenciaDestino;
    }

    public String getNumeroContaDestino() {
        return numeroContaDestino;
    }

    public void setNumeroContaDestino(String numeroContaDestino) {
        this.numeroContaDestino = numeroContaDestino;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
