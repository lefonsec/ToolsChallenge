package com.toolschallenge.prova.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoRequest {
    @NotNull
    private TransacaoDTO transacao;
}
