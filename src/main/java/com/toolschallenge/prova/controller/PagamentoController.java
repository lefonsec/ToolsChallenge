package com.toolschallenge.prova.controller;

import com.toolschallenge.prova.dto.ListaTransacoesResponse;
import com.toolschallenge.prova.dto.TransacaoRequest;
import com.toolschallenge.prova.dto.TransacaoResponse;
import com.toolschallenge.prova.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping("pagamento")
    public ResponseEntity<TransacaoResponse> realizarPagamento(@Valid @RequestBody TransacaoRequest transacaoRquest) {
            TransacaoResponse resposta = pagamentoService.realizarPagamento(transacaoRquest.getTransacao());
            return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }

    @PostMapping("estorno")
    public ResponseEntity<TransacaoResponse> estornarPagamento(@RequestParam String id) {
        TransacaoResponse resposta = pagamentoService.estornarPagamento(id);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }

    @GetMapping("consulta/{id}")
    public ResponseEntity<TransacaoResponse> consultarPagamento(@PathVariable String id) {
        TransacaoResponse resposta = pagamentoService.consultarPagamentoPorID(id);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("transacoes")
    public ResponseEntity<ListaTransacoesResponse> consultarTodosPagamentos(@RequestParam(defaultValue = "0") int pagina, @RequestParam(defaultValue = "10") int tamanhoPagina) {
        ListaTransacoesResponse response = pagamentoService.consultarTodosPagamentos(pagina, tamanhoPagina);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
