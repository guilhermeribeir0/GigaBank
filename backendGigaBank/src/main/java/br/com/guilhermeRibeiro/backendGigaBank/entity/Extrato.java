package br.com.guilhermeRibeiro.backendGigaBank.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_extrato")
public class Extrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private ContaBancaria contaBancaria;

    @Column(name = "tipo_operacao")
    private String tipoOperacao;

    private Double valor;

    @Column(name = "data_operacao")
    private LocalDateTime dataOperacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDateTime dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    @Override
    public String toString() {
        return "Extrato{" +
                "id=" + id +
                ", contaBancaria=" + contaBancaria +
                ", tipoOperacao='" + tipoOperacao + '\'' +
                ", valor=" + valor +
                ", dataOperacao=" + dataOperacao +
                '}';
    }
}
