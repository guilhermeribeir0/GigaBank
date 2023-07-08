package br.com.guilhermeRibeiro.backendGigaBank.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DepositoDTO implements Serializable {

    private String numeroConta;

    private String agenciaConta;

    private Double valor;
}
