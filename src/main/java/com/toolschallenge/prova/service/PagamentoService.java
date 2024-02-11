package com.toolschallenge.prova.service;

import com.toolschallenge.prova.dto.ListaTransacoesResponse;
import com.toolschallenge.prova.dto.TransacaoDTO;
import com.toolschallenge.prova.dto.TransacaoResponse;
import com.toolschallenge.prova.exception.NotFoundException;
import com.toolschallenge.prova.model.Transacao;
import com.toolschallenge.prova.model.enuns.Status;
import com.toolschallenge.prova.repository.DescricaoRepository;
import com.toolschallenge.prova.repository.FormaPagamentoRepository;
import com.toolschallenge.prova.repository.TransacaoRepository;
import com.toolschallenge.prova.utils.TransacaoServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PagamentoService {

    private final TransacaoRepository transacaoRepository;
    private final DescricaoRepository descricaoRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final TransacaoServiceUtils transacaoServiceUtils;

    @Transactional
    public TransacaoResponse realizarPagamento(TransacaoDTO transacaoDTO) {
        transacaoServiceUtils.validarTransacao(transacaoDTO, transacaoRepository);
        Transacao transacao = transacaoServiceUtils.criarTransacao(transacaoDTO, descricaoRepository, formaPagamentoRepository);
        Transacao transacaoSalva = transacaoRepository.save(transacao);
        return transacaoServiceUtils.construirResposta(transacaoSalva);
    }

    @Transactional
    public TransacaoResponse estornarPagamento(String id) {
        Optional<Transacao> optionalTransacao = transacaoRepository.findById(Long.parseLong(id));
        if (optionalTransacao.isEmpty()) {
            throw new NotFoundException("ID não encontrado, não pode ter estorno.");
        }

        Transacao transacao = optionalTransacao.get();
        transacao.getDescricao().setStatus(Status.CANCELADO);
        Transacao transacaoEstornada = transacaoRepository.save(transacao);

        return transacaoServiceUtils.construirResposta(transacaoEstornada);
    }

    @Transactional(readOnly = true)
    public TransacaoResponse consultarPagamentoPorID(String id) {
        Optional<Transacao> optionalTransacao = transacaoRepository.findById(Long.parseLong(id));
        if (optionalTransacao.isEmpty()) {
            throw new NotFoundException("ID não encontrado.");
        }
        Transacao transacao = optionalTransacao.get();

        TransacaoResponse response = new TransacaoResponse();
        response.setTransacao(transacaoServiceUtils.construirTransacaoDTO(transacao));

        return response;
    }

    @Transactional(readOnly = true)
    public ListaTransacoesResponse consultarTodosPagamentos(int pagina, int tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        Page<Transacao> pageTransacoes = transacaoRepository.findAll(pageable);

        List<TransacaoDTO> transacoesDTO = pageTransacoes.getContent().stream().map(transacaoServiceUtils::construirTransacaoDTO).collect(Collectors.toList());

        ListaTransacoesResponse response = new ListaTransacoesResponse();
        response.setTransacoes(transacoesDTO);

        return response;
    }
}
