package br.com.guilhermeRibeiro.backendGigaBank.dto.request.extract;

import java.io.Serializable;
import java.time.LocalDate;

public class ExtractRequest implements Serializable {

    private static final Long serialVersionUID = 1L;
    private String agency;
    private String acoountNumber;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAcoountNumber() {
        return acoountNumber;
    }

    public void setAcoountNumber(String acoountNumber) {
        this.acoountNumber = acoountNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
