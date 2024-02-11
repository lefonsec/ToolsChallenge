package com.toolschallenge.prova.utils;

import com.toolschallenge.prova.dto.DescricaoDTO;
import com.toolschallenge.prova.dto.FormaPagamentoDTO;
import com.toolschallenge.prova.dto.TransacaoDTO;
import com.toolschallenge.prova.dto.TransacaoResponse;
import com.toolschallenge.prova.exception.DataIntegrityViolationException;
import com.toolschallenge.prova.exception.ValidationException;
import com.toolschallenge.prova.model.Descricao;
import com.toolschallenge.prova.model.FormaPagamento;
import com.toolschallenge.prova.model.Transacao;
import com.toolschallenge.prova.model.enuns.Status;
import com.toolschallenge.prova.model.enuns.TipoPagamento;
import com.toolschallenge.prova.repository.DescricaoRepository;
import com.toolschallenge.prova.repository.FormaPagamentoRepository;
import com.toolschallenge.prova.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TransacaoServiceUtilsTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoServiceUtils transacaoServiceUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveLancarExcecaoSeIdJaExistir() {
        TransacaoDTO transacaoDTO = configurarTransacao(1L, "1234567890123456", BigDecimal.ONE, LocalDateTime.now(), TipoPagamento.AVISTA, 1);
        when(transacaoRepository.findById(transacaoDTO.getId())).thenReturn(Optional.of(new Transacao()));

        assertThrows(DataIntegrityViolationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });

        verify(transacaoRepository, times(1)).findById(transacaoDTO.getId());
    }

    @Test
    public void deveLancarExcecaoSeCartaoForNull() {
        TransacaoDTO transacaoDTO = configurarTransacao(null, null, null, null, null, 0);

        assertThrows(ValidationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });
    }

    @Test
    public void deveLancarExcecaoSeIdForNull() {
        TransacaoDTO transacaoDTO = configurarTransacao(null, "1234567890123456", null, null, null, 0);

        assertThrows(ValidationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });
    }

    @Test
    public void deveLancarExcecaoSeDescricaoValorForNull() {
        TransacaoDTO transacaoDTO = configurarTransacao(1L, "1234567890123456", null, null, null, 0);

        assertThrows(ValidationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });
    }

    @Test
    public void deveLancarExcecaoSeDescricaoValorForZero() {
        TransacaoDTO transacaoDTO = configurarTransacao(1L, "1234567890123456", BigDecimal.ZERO, LocalDateTime.now(), null, 0);

        assertThrows(ValidationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });
    }

    @Test
    public void deveLancarExcecaoSeDescricaoDataHoraForNull() {
        TransacaoDTO transacaoDTO = configurarTransacao(1L, "1234567890123456", BigDecimal.ONE, null, null, 0);

        assertThrows(ValidationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });
    }

    @Test
    public void deveLancarExcecaoSeFormaPagamentoTipoForNull() {
        TransacaoDTO transacaoDTO = configurarTransacao(1L, "1234567890123456", BigDecimal.ONE, LocalDateTime.now(), null, 0);

        assertThrows(ValidationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });
    }

    @Test
    public void deveLancarExcecaoSeFormaPagamentoParcelaForZero() {
        TransacaoDTO transacaoDTO = configurarTransacao(1L, "1234567890123456", BigDecimal.ONE, LocalDateTime.now(), TipoPagamento.AVISTA, 0);

        assertThrows(ValidationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });
    }

    @Test
    public void deveLancarExcecaoSeParcelaForMaiorQueUmETipoForAvista() {
        TransacaoDTO transacaoDTO = configurarTransacao(1L, "1234567890123456", BigDecimal.ONE, LocalDateTime.now(), TipoPagamento.AVISTA, 2);

        assertThrows(ValidationException.class, () -> {
            transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        });
    }

    @Test
    public void deveValidarTransacaoComEntradaValida() {
        TransacaoDTO transacaoDTO = configurarTransacao(1L, "1234567890123456", BigDecimal.ONE, LocalDateTime.now(), TipoPagamento.PARCELADO_LOJA, 2);

        transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
    }

    @Test
    public void testarCriarTransacao() {
        TransacaoDTO transacaoDTO = configurarTransacaoDTOValida();
        DescricaoRepository descricaoRepository = mock(DescricaoRepository.class);
        FormaPagamentoRepository formaPagamentoRepository = mock(FormaPagamentoRepository.class);

        Transacao transacao = transacaoServiceUtils.criarTransacao(transacaoDTO, descricaoRepository, formaPagamentoRepository);

        assertNotNull(transacao);
    }

    @Test
    public void testarConstruirResposta() {
        Transacao transacao = configurarTransacaoValida();
        TransacaoResponse response = transacaoServiceUtils.construirResposta(transacao);

        assertNotNull(response);
        assertNotNull(response.getTransacao());
    }

    @Test
    public void testarConstruirTransacaoDTO() {
        Transacao transacao = configurarTransacaoValida();
        TransacaoDTO transacaoDTO = transacaoServiceUtils.construirTransacaoDTO(transacao);

        assertNotNull(transacaoDTO);
    }

    private TransacaoDTO configurarTransacao(Long id, String cartao, BigDecimal valor, LocalDateTime dataHora, TipoPagamento tipoPagamento, int parcela) {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setId(id);
        transacaoDTO.setCartao(cartao);
        DescricaoDTO descricaoDTO = new DescricaoDTO();
        descricaoDTO.setValor(valor);
        descricaoDTO.setDataHora(dataHora);
        transacaoDTO.setDescricao(descricaoDTO);
        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
        formaPagamentoDTO.setTipo(tipoPagamento);
        formaPagamentoDTO.setParcela(parcela);
        transacaoDTO.setFormaPagamento(formaPagamentoDTO);
        return transacaoDTO;
    }

    private TransacaoDTO configurarTransacaoDTOValida() {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setId(1L);
        transacaoDTO.setCartao("1234567890123456");
        DescricaoDTO descricaoDTO = new DescricaoDTO();
        descricaoDTO.setValor(BigDecimal.ONE);
        descricaoDTO.setDataHora(LocalDateTime.now());
        transacaoDTO.setDescricao(descricaoDTO);
        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
        formaPagamentoDTO.setTipo(TipoPagamento.PARCELADO_LOJA);
        formaPagamentoDTO.setParcela(2);
        transacaoDTO.setFormaPagamento(formaPagamentoDTO);
        return transacaoDTO;
    }
    private Transacao configurarTransacaoValida() {
        Transacao transacao = new Transacao();
        transacao.setId(1L);

        Descricao descricao = new Descricao();
        descricao.setId(UUID.randomUUID());
        descricao.setStatus(Status.AUTORIZADO);
        descricao.setNsu("1234567890");
        descricao.setCodigoAutorizacao("ABCDE12345");
        transacao.setDescricao(descricao);

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(UUID.randomUUID());
        formaPagamento.setTipo(TipoPagamento.PARCELADO_LOJA);
        formaPagamento.setParcela(2);
        transacao.setFormaPagamento(formaPagamento);

        return transacao;
    }
}
