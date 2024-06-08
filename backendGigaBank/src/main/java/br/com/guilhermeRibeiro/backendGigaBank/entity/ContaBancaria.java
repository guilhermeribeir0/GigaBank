package br.com.guilhermeRibeiro.backendGigaBank.entity;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.ContaBancariaRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "t_contaBancaria")
public class ContaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 4)
    private String agencia;

    @NotNull
    @Column(length = 6)
    private String numero;

    private BigDecimal saldo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private Boolean ativa;

    public ContaBancaria() {}

    public ContaBancaria(ContaBancariaRequest request, Cliente cliente) {
        this.agencia = request.getAgencia();
        this.numero = request.getNumero();
        this.saldo = BigDecimal.valueOf(request.getSaldo());
        this.ativa = request.getAtiva();
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }
}
