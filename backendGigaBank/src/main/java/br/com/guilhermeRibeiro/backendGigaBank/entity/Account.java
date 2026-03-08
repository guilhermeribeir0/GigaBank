package br.com.guilhermeRibeiro.backendGigaBank.entity;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.contaBancaria.AccountRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "t_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 4)
    private String agency;

    @NotNull
    @Column(length = 6)
    private String number;

    private BigDecimal balance;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    private Boolean active;

    public Account() {}

    public Account(AccountRequest request, Customer customer) {
        this.agency = request.getAgency();
        this.number = request.getNumber();
        this.balance = BigDecimal.valueOf(request.getBalance());
        this.customer = customer;
        this.active = request.getActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String numero) {
        this.number = numero;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
