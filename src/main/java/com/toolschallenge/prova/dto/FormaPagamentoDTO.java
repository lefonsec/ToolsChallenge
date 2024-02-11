package com.toolschallenge.prova.dto;

import com.toolschallenge.prova.model.enuns.TipoPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoDTO {
    @NotNull(message = "Tipo Pagamento não pode ser Nulo ou vazio")
    private TipoPagamento tipo;

    @NotNull(message = "Preencher parcela")
    private Integer parcela;
}
