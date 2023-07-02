package br.com.guilhermeRibeiro.backendGigaBank.entity;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
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

    @Column(length = 4)
    private String agencia;

    @Column(length = 6)
    private String numero;

    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private Boolean ativo;
}
