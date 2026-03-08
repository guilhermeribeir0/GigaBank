package br.com.guilhermeRibeiro.backendGigaBank.entity;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente.CustomerRequest;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @CPF
    @NotNull
    @Column(length = 11)
    private String cpf;

    @Email
    @Column(length = 40)
    private String email;

    private Boolean active;

    public Customer() {}

    public Customer(CustomerRequest request) {
        this.name = request.getName();
        this.cpf = request.getCpf();
        this.email = request.getEmail();
        this.active = request.getActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void changeActive() {
        if (this.getActive() ) {
            this.active = false;
        } else {
            this.active = true;
        }
    }
}
