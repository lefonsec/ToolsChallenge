package com.toolschallenge.prova.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDTO {

    @NotEmpty(message = "Preencher numero do Cartão")
    private String cartao;

    @NotNull(message = "ID não pode ser nulo")
    private Long id;

    @NotNull(message = "Preencher descrição")
    private DescricaoDTO descricao;

    @NotNull(message = "Preencher forma pagamento")
    private FormaPagamentoDTO formaPagamento;
}
