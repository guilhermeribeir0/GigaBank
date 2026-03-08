package br.com.guilhermeRibeiro.backendGigaBank.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_extrato")
public class Extrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Account account;

    @Column(name = "tipo_operacao")
    private String tipoOperacao;

    private BigDecimal valor;

    @Column(name = "data_operacao")
    private LocalDateTime dataOperacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getContaBancaria() {
        return account;
    }

    public void setContaBancaria(Account account) {
        this.account = account;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
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
                ", contaBancaria=" + account +
                ", tipoOperacao='" + tipoOperacao + '\'' +
                ", valor=" + valor +
                ", dataOperacao=" + dataOperacao +
                '}';
    }
}
