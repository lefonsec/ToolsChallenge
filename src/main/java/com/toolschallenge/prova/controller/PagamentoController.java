package com.toolschallenge.prova.controller;

import com.toolschallenge.prova.dto.TransacaoDTO;
import com.toolschallenge.prova.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping("/pagamento")
    public ResponseEntity<TransacaoDTO> realizarPagamento(@Valid @RequestBody TransacaoDTO transacaoDTO) {
        TransacaoDTO resposta = pagamentoService.realizarPagamento(transacaoDTO);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping("/estorno")
    public ResponseEntity<TransacaoDTO> estornarPagamento(@RequestParam String id) {
        TransacaoDTO resposta = pagamentoService.estornarPagamento(id);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/consulta")
    public ResponseEntity<TransacaoDTO> consultarPagamento(@RequestParam String id) {
        TransacaoDTO resposta = pagamentoService.consultarPagamentoPorID(id);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }
}
