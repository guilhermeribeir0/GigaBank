package br.com.guilhermeRibeiro.backendGigaBank.entity;

import br.com.guilhermeRibeiro.backendGigaBank.dto.ContaBancariaDTO;
import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "t_contaBancaria")
@Data
@EqualsAndHashCode
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

    private Double saldo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private Boolean ativa;

    public ContaBancaria() {}

    public ContaBancaria(ContaBancariaDTO contaBancariaDTO, Cliente cliente) {
        this.agencia = contaBancariaDTO.getAgencia();
        this.numero = contaBancariaDTO.getNumero();
        this.saldo = contaBancariaDTO.getSaldo();
        this.ativa = contaBancariaDTO.getAtiva();
        this.cliente = cliente;
    }
}
