package br.com.guilhermeRibeiro.backendGigaBank.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExtratoDTO implements Serializable {

    private Double valor;

    private String tipoOperacao;

    private LocalDateTime dataOperacao;
}
