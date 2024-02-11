package com.toolschallenge.prova.service;

import com.toolschallenge.prova.dto.*;
import com.toolschallenge.prova.exception.NotFoundException;
import com.toolschallenge.prova.model.Descricao;
import com.toolschallenge.prova.model.FormaPagamento;
import com.toolschallenge.prova.model.Transacao;
import com.toolschallenge.prova.model.enuns.Status;
import com.toolschallenge.prova.model.enuns.TipoPagamento;
import com.toolschallenge.prova.repository.TransacaoRepository;
import com.toolschallenge.prova.utils.TransacaoServiceUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private TransacaoServiceUtils transacaoServiceUtils;

    @InjectMocks
    private PagamentoService pagamentoService;

    @Test
    void testeEstornarPagamento() {
        String id = "1";
        Transacao transacao = getTransacao();
        TransacaoDTO transacaoDTO = getTransacaoDTO(Status.CANCELADO);
        TransacaoResponse transacaoResponse = new TransacaoResponse(transacaoDTO);
        transacao.getDescricao().setStatus(Status.CANCELADO);
        when(transacaoRepository.findById(Long.parseLong(id))).thenReturn(Optional.of(transacao));
        when(transacaoRepository.save(Mockito.any(Transacao.class))).thenReturn(transacao);
        when(transacaoServiceUtils.construirResposta(any())).thenReturn(transacaoResponse);

        TransacaoResponse resultado = pagamentoService.estornarPagamento(id);

        assertEquals(Status.CANCELADO, resultado.getTransacao().getDescricao().getStatus());
    }

    @Test
    void testeEstornarPagamentoComNotFoundException() {
        String id = "1";
        when(transacaoRepository.findById(Long.parseLong(id))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pagamentoService.estornarPagamento(id));
    }

    @Test
    void testeConsultarPagamentoPorIDComNotFoundException() {
        String id = "1";
        when(transacaoRepository.findById(Long.parseLong(id))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pagamentoService.consultarPagamentoPorID(id));
    }

    @Test
    void testeConsultarTodosPagamentos() {
        int pagina = 0;
        int tamanhoPagina = 10;

        when(transacaoRepository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));
        Mockito.lenient().when(transacaoServiceUtils.construirTransacaoDTO(Mockito.any(Transacao.class))).thenReturn(new TransacaoDTO());
        ListaTransacoesResponse resultado = pagamentoService.consultarTodosPagamentos(pagina, tamanhoPagina);

        assertEquals(0, resultado.getTransacoes().size());
    }

    private Transacao getTransacao() {
        return new Transacao(1L, "123", new Descricao(UUID.randomUUID(), BigDecimal.TEN, LocalDateTime.now(), "PETSHOP", "123", "1234", Status.AUTORIZADO), new FormaPagamento(UUID.randomUUID(), TipoPagamento.AVISTA, 1));
    }

    private TransacaoDTO getTransacaoDTO(Status status) {
        return new TransacaoDTO("123456", 1L, new DescricaoDTO(BigDecimal.TEN, LocalDateTime.now(), "PETSHOP", "123", "1234", status), new FormaPagamentoDTO(TipoPagamento.AVISTA, 1));
    }
}