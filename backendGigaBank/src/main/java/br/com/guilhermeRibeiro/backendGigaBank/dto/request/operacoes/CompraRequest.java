package br.com.guilhermeRibeiro.backendGigaBank.dto.request.operacoes;

import java.io.Serializable;
import java.time.LocalDate;

public class CompraRequest implements Serializable {

    private static final Long serialVersionUID = 1L;
    private String cpfCliente;
    private String numeroCartao;
    private String cvv;
    private LocalDate dataVencimento;
    private Double valor;

    public CompraRequest() {
    }

    public CompraRequest(String cpfCliente, String numeroCartao, String cvv, LocalDate dataVencimento, Double valor) {
        this.cpfCliente = cpfCliente;
        this.numeroCartao = numeroCartao;
        this.cvv = cvv;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
