package br.com.guilhermeRibeiro.backendGigaBank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositoDTO implements Serializable {

    private String numeroConta;

    private String agenciaConta;

    private Double valor;
}
