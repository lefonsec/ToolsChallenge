package com.toolschallenge.prova.dto;

import com.toolschallenge.prova.model.enuns.TipoPagamento;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoDTO {
    @NotBlank
    private TipoPagamento tipo;

    @NotBlank
    private Integer parcela;
}
