package br.com.guilhermeRibeiro.backendGigaBank.dto.response.operacoes;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExtratoResponse implements Serializable {

    private Double valor;
    private String tipoOperacao;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataOperacao;

    public ExtratoResponse() {
    }

    public ExtratoResponse(Double valor, String tipoOperacao, LocalDateTime dataOperacao) {
        this.valor = valor;
        this.tipoOperacao = tipoOperacao;
        this.dataOperacao = dataOperacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public LocalDateTime getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDateTime dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    @Override
    public String toString() {
        return "ExtratoResponse{" +
                "valor=" + valor +
                ", tipoOperacao='" + tipoOperacao + '\'' +
                ", dataOperacao=" + dataOperacao +
                '}';
    }
}
