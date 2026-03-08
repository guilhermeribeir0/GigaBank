package br.com.guilhermeRibeiro.backendGigaBank.dto.response.account;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Customer;

public class AccountResponse implements Serializable {

    private Long id;
    private String agency;
    private String number;
    private BigDecimal balance;
    private Customer customer;
    private Boolean active;

    public AccountResponse() {}

    public AccountResponse(Long id, String agency, String number, BigDecimal balance, Customer customer, Boolean active) {
        this.id = id;
        this.agency = agency;
        this.number = number;
        this.balance = balance;
        this.customer = customer;
        this.active = active;
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

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
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

    @Override
    public String toString() {
        return "AccountResponse{" +
                "id=" + id +
                ", agency='" + agency + '\'' +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", customer=" + customer +
                ", active=" + active +
                '}';
    }
}
