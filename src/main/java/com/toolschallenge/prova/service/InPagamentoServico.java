package com.toolschallenge.prova.service;

import com.toolschallenge.prova.dto.ListaTransacoesResponse;
import com.toolschallenge.prova.dto.TransacaoDTO;
import com.toolschallenge.prova.dto.TransacaoResponse;

public interface InPagamentoServico {
    TransacaoResponse realizarPagamento(TransacaoDTO transacaoDTO);
    TransacaoResponse estornarPagamento(String id);
    TransacaoResponse consultarPagamentoPorID(String id);
    ListaTransacoesResponse consultarTodosPagamentos(int pagina, int tamanhoPagina);
}
