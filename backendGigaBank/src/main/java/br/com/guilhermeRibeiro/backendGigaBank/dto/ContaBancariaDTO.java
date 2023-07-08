package br.com.guilhermeRibeiro.backendGigaBank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaBancariaDTO {

    private String clienteNome;

    private String numero;

    private String agencia;

    private Double saldo;

    private Boolean ativa;
}
