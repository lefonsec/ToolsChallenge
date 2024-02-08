package com.toolschallenge.prova.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String cartao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "descricao_id")
    private Descricao descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

}