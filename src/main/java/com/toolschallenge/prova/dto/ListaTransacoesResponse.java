package com.toolschallenge.prova.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListaTransacoesResponse {
    private List<TransacaoDTO> transacoes;
}
