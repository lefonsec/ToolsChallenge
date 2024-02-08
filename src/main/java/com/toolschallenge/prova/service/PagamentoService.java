package com.toolschallenge.prova.service;

import com.toolschallenge.prova.dto.DescricaoDTO;
import com.toolschallenge.prova.dto.FormaPagamentoDTO;
import com.toolschallenge.prova.dto.TransacaoDTO;
import com.toolschallenge.prova.model.Descricao;
import com.toolschallenge.prova.model.FormaPagamento;
import com.toolschallenge.prova.model.Transacao;
import com.toolschallenge.prova.model.enuns.Status;
import com.toolschallenge.prova.repository.DescricaoRepository;
import com.toolschallenge.prova.repository.FormaPagamentoRepository;
import com.toolschallenge.prova.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PagamentoService {

    private final TransacaoRepository transacaoRepository;
    private final DescricaoRepository descricaoRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;

    public TransacaoDTO realizarPagamento(TransacaoDTO transacaoDTO) {
        Transacao transacao = new Transacao();
        BeanUtils.copyProperties(transacaoDTO, transacao);

        Descricao descricao = new Descricao();
        BeanUtils.copyProperties(transacaoDTO.getDescricao(), descricao);
//        descricao.setValor(transacaoDTO.getDescricao().getValor());
//        descricao.setDataHora(transacaoDTO.getDescricao().getDataHora());
//        descricao.setEstabelecimento(transacaoDTO.getDescricao().getEstabelecimento());

        FormaPagamento formaPagamento = new FormaPagamento();
        BeanUtils.copyProperties(transacaoDTO.getFormaPagamento(), formaPagamento);
//        formaPagamento.setTipo(transacaoDTO.getFormaPagamento().getTipo());
//        formaPagamento.setParcela(transacaoDTO.getFormaPagamento().getParcela());
        Random random = new Random();
        boolean autorizado = random.nextBoolean();
        descricao.setStatus(autorizado ? Status.AUTORIZADO : Status.NEGADO);

        descricaoRepository.save(descricao);
        formaPagamentoRepository.save(formaPagamento);

        transacao.setDescricao(descricao);
        transacao.setFormaPagamento(formaPagamento);

        Transacao transacaoSalva = transacaoRepository.save(transacao);
        return construirTransacaoDTO(transacaoSalva);
    }

    public TransacaoDTO estornarPagamento(String id) {
        Optional<Transacao> buscaTransacao = transacaoRepository.findById(Long.parseLong(id));
        if (buscaTransacao.isEmpty()) {
            return null;
        }

        Transacao transacao = buscaTransacao.get();
        transacao.getDescricao().setStatus(Status.CANCELADO);
        Transacao transacaoEstornada = transacaoRepository.save(transacao);
        return construirTransacaoDTO(transacaoEstornada);
    }

    public TransacaoDTO consultarPagamentoPorID(String id) {
        return null;
    }

    public TransacaoDTO consultarPagamentos(String id) {
        return null;
    }

    private TransacaoDTO construirTransacaoDTO(Transacao transacao) {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setCartao(transacao.getCartao());
        transacaoDTO.setId(UUID.fromString(transacao.getId().toString()));

        DescricaoDTO descricaoDTO = new DescricaoDTO();
        descricaoDTO.setValor(transacao.getDescricao().getValor());
        descricaoDTO.setDataHora(transacao.getDescricao().getDataHora());
        descricaoDTO.setEstabelecimento(transacao.getDescricao().getEstabelecimento());
        descricaoDTO.setNsu(transacao.getDescricao().getNsu());
        descricaoDTO.setCodigoAutorizacao(transacao.getDescricao().getCodigoAutorizacao());
        descricaoDTO.setStatus(transacao.getDescricao().getStatus());

        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
        formaPagamentoDTO.setTipo(transacao.getFormaPagamento().getTipo());
        formaPagamentoDTO.setParcela(transacao.getFormaPagamento().getParcela());

        transacaoDTO.setDescricao(descricaoDTO);
        transacaoDTO.setFormaPagamento(formaPagamentoDTO);

        return transacaoDTO;
    }
}
