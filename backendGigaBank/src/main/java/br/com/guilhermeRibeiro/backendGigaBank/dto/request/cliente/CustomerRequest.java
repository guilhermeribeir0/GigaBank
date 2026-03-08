package br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente;

public class CustomerRequest {

    private String name;
    private String cpf;
    private String email;
    private Boolean active;

    public CustomerRequest() {}

    public CustomerRequest(String name, String cpf, String email, Boolean active) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
