package com.toolschallenge.prova.dto;

import com.toolschallenge.prova.model.enuns.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class DescricaoDTO {

    @NotBlank
    private BigDecimal valor;

    @NotBlank
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

    @NotBlank
    private String estabelecimento;

    private String nsu;

    @Column(name = "codigo_autorizacao")
    private String codigoAutorizacao;

    @Enumerated(EnumType.STRING)
    private Status status;
}
