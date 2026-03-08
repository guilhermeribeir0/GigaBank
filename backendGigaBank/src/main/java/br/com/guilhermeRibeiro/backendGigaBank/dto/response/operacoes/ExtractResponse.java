package br.com.guilhermeRibeiro.backendGigaBank.dto.response.operacoes;

import br.com.guilhermeRibeiro.backendGigaBank.entity.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExtractResponse implements Serializable {

    private Double value;
    private OperationType operationType;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime operationDate;

    public ExtractResponse() {
    }

    public ExtractResponse(Double value, OperationType operationType, LocalDateTime operationDate) {
        this.value = value;
        this.operationType = operationType;
        this.operationDate = operationDate;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    @Override
    public String toString() {
        return "ExtractResponse{" +
                "value=" + value +
                ", operationType='" + operationType + '\'' +
                ", operationDate=" + operationDate +
                '}';
    }
}
