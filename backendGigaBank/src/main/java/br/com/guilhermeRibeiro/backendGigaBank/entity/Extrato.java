package br.com.guilhermeRibeiro.backendGigaBank.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_extrato")
@Data
@EqualsAndHashCode
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
}
