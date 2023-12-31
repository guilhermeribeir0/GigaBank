package br.com.guilhermeRibeiro.backendGigaBank.entity;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.ClienteRequest;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_clientes")
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

    public Cliente(ClienteRequest request) {
        this.nome = request.getNome();
        this.cpf = request.getCpf();
        this.email = request.getEmail();
        this.ativo = request.getAtivo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
