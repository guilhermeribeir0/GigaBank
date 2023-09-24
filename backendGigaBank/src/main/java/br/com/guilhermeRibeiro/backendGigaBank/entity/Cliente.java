package br.com.guilhermeRibeiro.backendGigaBank.entity;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_clientes")
@Data
@EqualsAndHashCode
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;
    @CPF
    @NotNull
    @Column(length = 11)
    private String cpf;

    @Email
    @Column(length = 40)
    private String email;

    private Boolean ativo;

    public Cliente() {}

    public Cliente(String nome, String cpf, String email, Boolean ativo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.ativo = ativo;
    }
}
