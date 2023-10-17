package br.com.guilhermeRibeiro.backendGigaBank.dto.request.cliente;

public class ClienteRequest {

    private String nome;
    private String cpf;
    private String email;
    private Boolean ativo;
    public ClienteRequest() {}

    public ClienteRequest(String nome, String cpf, String email, Boolean ativo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.ativo = ativo;
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
