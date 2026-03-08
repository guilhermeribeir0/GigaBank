package br.com.guilhermeRibeiro.backendGigaBank.dto.response.cliente;

import java.io.Serializable;

public class CustomerResponse implements Serializable {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private Boolean active;

    public CustomerResponse() {}

    public CustomerResponse(Long id, String name, String cpf, String email, Boolean active) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.active = active;
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

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
