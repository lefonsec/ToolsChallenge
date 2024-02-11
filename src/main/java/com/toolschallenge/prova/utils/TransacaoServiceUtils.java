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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class TransacaoServiceUtils {
    public void validarTransacao(TransacaoDTO transacaoDTO, TransacaoRepository transacaoRepository) {
        if (transacaoRepository.findById(transacaoDTO.getId()).isPresent()) {
            throw new DataIntegrityViolationException("ID Já cadastrado");
        }
        if (transacaoDTO.getCartao() == null || transacaoDTO.getCartao().isEmpty()) {
            throw new ValidationException("O campo 'cartao' é obrigatório");
        }
        if (transacaoDTO.getId() == null) {
            throw new ValidationException("O campo 'id' é obrigatório");
        }
        DescricaoDTO descricaoDTO = transacaoDTO.getDescricao();
        if (descricaoDTO == null || descricaoDTO.getValor() == null || descricaoDTO.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("O campo 'valor' da descrição é obrigatório e deve ser maior que zero");
        }
        if (descricaoDTO.getDataHora() == null) {
            throw new ValidationException("O campo 'DataHora' da descrição é obrigatório");
        }
        FormaPagamentoDTO formaPagamentoDTO = transacaoDTO.getFormaPagamento();
        if (formaPagamentoDTO == null || formaPagamentoDTO.getTipo() == null || formaPagamentoDTO.getParcela() <= 0) {
            throw new ValidationException("A forma de pagamento é obrigatória e a parcela deve ser maior que zero");
        }
        if (formaPagamentoDTO.getParcela() > 1 && formaPagamentoDTO.getTipo() == TipoPagamento.AVISTA) {
            throw new ValidationException("A parcela é maior que um então o pagamento não pode ser AVISTA, pode ser a penas PARCELADO_LOJA ou PARCELADO_EMISSOR ");
        }
    }

    public Transacao criarTransacao(TransacaoDTO transacaoDTO, DescricaoRepository descricaoRepository, FormaPagamentoRepository formaPagamentoRepository) {
        Transacao transacao = new Transacao();
        BeanUtils.copyProperties(transacaoDTO, transacao);
        preencherDescricao(transacaoDTO, transacao, descricaoRepository);
        preencherFormaPagamento(transacaoDTO, transacao, formaPagamentoRepository);
        return transacao;
    }

    public TransacaoResponse construirResposta(Transacao transacaoSalva) {
        TransacaoDTO transacaoSalvaDTO = new TransacaoDTO();
        BeanUtils.copyProperties(transacaoSalva, transacaoSalvaDTO);
        DescricaoDTO descricaoDTO = new DescricaoDTO();
        BeanUtils.copyProperties(transacaoSalva.getDescricao(), descricaoDTO);
        transacaoSalvaDTO.setDescricao(descricaoDTO);
        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
        BeanUtils.copyProperties(transacaoSalva.getFormaPagamento(), formaPagamentoDTO);
        transacaoSalvaDTO.setFormaPagamento(formaPagamentoDTO);
        TransacaoResponse response = new TransacaoResponse();
        response.setTransacao(transacaoSalvaDTO);
        return response;
    }

    public TransacaoDTO construirTransacaoDTO(Transacao transacao) {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        BeanUtils.copyProperties(transacao, transacaoDTO);
        transacaoDTO.setDescricao(construirDescricaoDTO(transacao.getDescricao()));
        transacaoDTO.setFormaPagamento(construirFormaPagamentoDTO(transacao.getFormaPagamento()));
        return transacaoDTO;
    }

    private DescricaoDTO construirDescricaoDTO(Descricao descricao) {
        DescricaoDTO descricaoDTO = new DescricaoDTO();
        BeanUtils.copyProperties(descricao, descricaoDTO);
        return descricaoDTO;
    }

    private FormaPagamentoDTO construirFormaPagamentoDTO(FormaPagamento formaPagamento) {
        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
        BeanUtils.copyProperties(formaPagamento, formaPagamentoDTO);
        return formaPagamentoDTO;
    }

    private void preencherDescricao(TransacaoDTO transacaoDTO, Transacao transacao, DescricaoRepository descricaoRepository) {
        Descricao descricao = new Descricao();
        BeanUtils.copyProperties(transacaoDTO.getDescricao(), descricao);
        Random random = new Random();
        boolean autorizado = random.nextBoolean();
        descricao.setStatus(autorizado ? Status.AUTORIZADO : Status.NEGADO);
        descricao.setNsu(String.valueOf(random.nextInt(1000)));
        descricao.setCodigoAutorizacao(String.valueOf(random.nextInt(100000)));
        descricaoRepository.save(descricao);
        transacao.setDescricao(descricao);
    }

    private void preencherFormaPagamento(TransacaoDTO transacaoDTO, Transacao transacao, FormaPagamentoRepository formaPagamentoRepository) {
        FormaPagamento formaPagamento = new FormaPagamento();
        BeanUtils.copyProperties(transacaoDTO.getFormaPagamento(), formaPagamento);
        formaPagamentoRepository.save(formaPagamento);
        transacao.setFormaPagamento(formaPagamento);
    }
}
