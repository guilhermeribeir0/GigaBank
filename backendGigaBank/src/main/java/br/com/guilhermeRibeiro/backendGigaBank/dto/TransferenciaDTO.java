package br.com.guilhermeRibeiro.backendGigaBank.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransferenciaDTO implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String agenciaOrigem;

    private String numeroContaOrigem;

    private String agenciaDestino;

    private String numeroContaDestino;

    private Double valor;
}
