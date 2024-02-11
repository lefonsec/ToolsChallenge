package com.toolschallenge.prova.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toolschallenge.prova.model.enuns.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DescricaoDTO {

    @NotNull
    private BigDecimal valor;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

    @NotNull
    private String estabelecimento;

    private String nsu;

    private String codigoAutorizacao;

    @Enumerated(EnumType.STRING)
    private Status status;
}
