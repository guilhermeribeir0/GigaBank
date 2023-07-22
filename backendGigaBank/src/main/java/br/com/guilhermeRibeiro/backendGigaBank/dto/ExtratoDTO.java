package br.com.guilhermeRibeiro.backendGigaBank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExtratoDTO implements Serializable {

    private Double valor;

    private String tipoOperacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataOperacao;
}
