package br.com.guilhermeRibeiro.backendGigaBank.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.guilhermeRibeiro.backendGigaBank.entity.enums.OperationType;

@Entity
@Table(name = "t_extract")
public class Extract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @Column(name = "operation_type")
    private OperationType operationType;

    private BigDecimal value;

    @Column(name = "operation_date")
    private LocalDateTime operationDate;

    public Extract(Account account, OperationType operationType, BigDecimal value) {
        this.account = account;
        this.operationType = operationType;
        this.value = value;
        this.operationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    @Override
    public String toString() {
        return "Extract{" +
                "id=" + id +
                ", account=" + account +
                ", operationType='" + operationType + '\'' +
                ", value=" + value +
                ", operationDate=" + operationDate +
                '}';
    }
}