package br.com.guilhermeRibeiro.backendGigaBank.entity;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "t_contaBancaria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    private Boolean ativo;
}
