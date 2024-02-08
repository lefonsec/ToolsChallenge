package com.toolschallenge.prova.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransacaoDTO {
    @NotBlank
    private String cartao;

    @NotBlank
    private UUID id;

    @NotBlank
    private DescricaoDTO descricao;

    @NotBlank
    private FormaPagamentoDTO formaPagamento;
}
