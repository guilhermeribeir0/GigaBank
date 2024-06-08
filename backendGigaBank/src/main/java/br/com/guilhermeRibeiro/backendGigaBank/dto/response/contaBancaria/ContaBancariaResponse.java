package br.com.guilhermeRibeiro.backendGigaBank.dto.response.contaBancaria;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContaBancariaResponse implements Serializable {

    private Long id;
    private String agencia;
    private String numero;
    private BigDecimal saldo;
    private Cliente cliente;
    private Boolean ativa;

    public ContaBancariaResponse() {}

    public ContaBancariaResponse(Long id, String agencia, String numero, BigDecimal saldo, Cliente cliente, Boolean ativa) {
        this.id = id;
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = saldo;
        this.cliente = cliente;
        this.ativa = ativa;
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

    @Override
    public String toString() {
        return "ContaBancariariaResponse{" +
                "id=" + id +
                ", agencia='" + agencia + '\'' +
                ", numero='" + numero + '\'' +
                ", saldo=" + saldo +
                ", cliente=" + cliente +
                ", ativa=" + ativa +
                '}';
    }
}
